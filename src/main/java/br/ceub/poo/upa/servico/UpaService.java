package br.ceub.poo.upa.servico;

import br.ceub.poo.upa.algoritmo.Busca;
import br.ceub.poo.upa.algoritmo.Ordenacao;
import br.ceub.poo.upa.estrutura.Fila;
import br.ceub.poo.upa.estrutura.FilaPrioridade;
import br.ceub.poo.upa.estrutura.Pilha;
import br.ceub.poo.upa.excecao.*;
import br.ceub.poo.upa.modelo.*;
import br.ceub.poo.upa.observador.ObservadorChamada;
import br.ceub.poo.upa.repositorio.PacienteRepository;
import br.ceub.poo.upa.triagem.ClassificadorRisco;
import br.ceub.poo.upa.util.ValidadorCpf;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class UpaService {

    private final Fila<Paciente> filaTriagem = new Fila<>();
    private final FilaPrioridade<Paciente> filaAtendimento = new FilaPrioridade<>();
    private final Pilha<Atendimento> historicoAtendimentos = new Pilha<>();

    private final PacienteRepository repository;
    private final ClassificadorRisco classificador;
    private final Clock clock;
    private final List<ObservadorChamada> observadores = new ArrayList<>();

    private int proximaSenha = 1;

    public UpaService(PacienteRepository repository, ClassificadorRisco classificador, Clock clock) {
        this.repository = repository;
        this.classificador = classificador;
        this.clock = clock;
    }

    public void registrarObservador(ObservadorChamada obs) {
        observadores.add(obs);
    }

    public Paciente cadastrarPaciente(String nome, String cpf, LocalDate dataNascimento) throws UpaException {
        if (!ValidadorCpf.isValido(cpf)) {
            throw new CpfInvalidoException(cpf);
        }

        Optional<Paciente> existente = repository.buscarPorCpf(cpf);
        if (existente.isPresent() && existente.get().getStatus() != StatusPaciente.ATENDIDO) {
            throw new PacienteJaCadastradoException(cpf);
        }

        Paciente p = new Paciente(nome, cpf, dataNascimento, proximaSenha++, LocalDateTime.now(clock));
        repository.salvar(p);
        filaTriagem.enfileirar(p);
        return p;
    }

    public Paciente triarProximo(Enfermeiro enfermeiro, String queixa, SinaisVitais sv) throws UpaException {
        if (filaTriagem.estaVazia()) {
            throw new FilaVaziaException("Fila de triagem vazia.");
        }

        Paciente paciente = filaTriagem.espiar();
        Prioridade prioridade = classificador.classificar(sv);
        Triagem triagem = new Triagem(queixa, sv, enfermeiro, prioridade, LocalDateTime.now(clock));

        paciente.registrarTriagem(triagem);
        filaTriagem.desenfileirar();
        filaAtendimento.enfileirar(paciente);

        return paciente;
    }

    public Atendimento chamarProximo(Medico medico) throws UpaException {
        if (filaAtendimento.estaVazia()) {
            throw new FilaVaziaException("Fila de atendimento vazia.");
        }

        Paciente paciente = filaAtendimento.desenfileirar();
        paciente.marcarComoAtendido();

        Atendimento atendimento = new Atendimento(paciente, medico, LocalDateTime.now(clock));
        historicoAtendimentos.empilhar(atendimento);

        for (ObservadorChamada obs : observadores) {
            obs.notificarChamada(atendimento);
        }

        return atendimento;
    }

    public List<Paciente> getFilaTriagem() {
        List<Paciente> lista = new ArrayList<>();
        for (Paciente p : filaTriagem) lista.add(p);
        return lista;
    }

    public List<Paciente> getFilaAtendimento() {
        List<Paciente> lista = new ArrayList<>();
        for (Paciente p : filaAtendimento) lista.add(p);
        return lista;
    }

    public Paciente buscarPacientePorCpf(String cpf) throws PacienteNaoEncontradoException {
        return repository.buscarPorCpf(cpf)
                .orElseThrow(() -> new PacienteNaoEncontradoException(cpf));
    }

    public Paciente buscarPacientePorSenha(int senha) throws PacienteNaoEncontradoException {
        List<Paciente> todos = repository.listarTodos();
        todos.sort(Comparator.comparingInt(Paciente::getSenha));

        int idx = Busca.buscaBinariaRecursiva(todos, senha, 0, todos.size() - 1);
        if (idx == -1) {
            throw new PacienteNaoEncontradoException("Senha " + senha);
        }
        return todos.get(idx);
    }

    public List<Atendimento> relatorioUltimosAtendimentos(int qtd) {
        List<Atendimento> resultado = new ArrayList<>();
        List<Atendimento> todos = new ArrayList<>();
        for (Atendimento a : historicoAtendimentos) todos.add(a);

        for (int i = 0; i < Math.min(qtd, todos.size()); i++) {
            resultado.add(todos.get(i));
        }
        return resultado;
    }

    public List<Atendimento> relatorioPorTempoEspera() {
        List<Atendimento> todos = new ArrayList<>();
        for (Atendimento a : historicoAtendimentos) todos.add(a);

        return Ordenacao.mergeSort(todos, Comparator.comparingLong(Atendimento::getTempoEsperaMin).reversed());
    }

    public Map<Prioridade, Integer> contarPorPrioridade() {
        Map<Prioridade, Integer> mapa = new EnumMap<>(Prioridade.class);
        for (Prioridade p : Prioridade.values()) mapa.put(p, 0);

        for (Atendimento a : historicoAtendimentos) {
            Prioridade p = a.getPaciente().getPrioridade();
            if (p != null) mapa.put(p, mapa.get(p) + 1);
        }
        return mapa;
    }

    public double tempoMedioEsperaMin() {
        if (historicoAtendimentos.estaVazia()) return 0.0;
        long soma = 0;
        int count = 0;
        for (Atendimento a : historicoAtendimentos) {
            soma += a.getTempoEsperaMin();
            count++;
        }
        return (double) soma / count;
    }
}