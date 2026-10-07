package br.ceub.poo.upa.cli;

import br.ceub.poo.upa.excecao.UpaException;
import br.ceub.poo.upa.modelo.*;
import br.ceub.poo.upa.servico.UpaService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MenuCli {

    private final UpaService service;
    private final Enfermeiro enfermeiroPadrao;
    private final Medico medicoPadrao;

    public MenuCli(UpaService service) {
        this.service = service;
        this.enfermeiroPadrao = new Enfermeiro("Carla Mendes", "11122233344", LocalDate.of(1985, 5, 10), "COREN-DF 123456");
        this.medicoPadrao = new Medico("Paulo Lima", "55566677788", LocalDate.of(1980, 2, 15), "CRM-DF 45678", 1);
    }

    public void iniciar() {
        while (true) {
            exibirMenu();
            int opcao = Console.lerInt("Escolha uma opção: ");
            if (opcao == 0) {
                System.out.println("Encerrando o sistema UPA 24h. Até logo!");
                break;
            }
            try {
                processarOpcao(opcao);
            } catch (UpaException e) {
                System.out.println("  [ERRO] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("  [ERRO INESPERADO] " + e.getMessage());
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n===== UPA 24H — SISTEMA DE GESTÃO DE FILA =====");
        System.out.println("1. Cadastrar paciente (Recepção)");
        System.out.println("2. Realizar triagem (Enfermagem)");
        System.out.println("3. Chamar próximo paciente (Médico)");
        System.out.println("4. Visualizar filas");
        System.out.println("5. Buscar paciente");
        System.out.println("6. Relatórios de Atendimento");
        System.out.println("7. Estatísticas do Plantão");
        System.out.println("0. Sair");
    }

    private void processarOpcao(int opcao) throws UpaException {
        switch (opcao) {
            case 1 -> cadastrarPaciente();
            case 2 -> realizarTriagem();
            case 3 -> chamarProximo();
            case 4 -> exibirFilas();
            case 5 -> buscarPaciente();
            case 6 -> exibirRelatorios();
            case 7 -> exibirEstatisticas();
            default -> System.out.println("Opção inválida!");
        }
    }

    private void cadastrarPaciente() throws UpaException {
        System.out.println("\n--- CADASTRO DE PACIENTE ---");
        String nome = Console.lerString("Nome completo: ");
        String cpf = Console.lerString("CPF: ");
        LocalDate dataNasc = Console.lerData("Data de nascimento (dd/MM/yyyy): ");

        Paciente p = service.cadastrarPaciente(nome, cpf, dataNasc);
        System.out.println("  [OK] Paciente cadastrado com sucesso! Senha: " + p.getSenhaFormatada());
    }

    private void realizarTriagem() throws UpaException {
        System.out.println("\n--- TRIAGEM (" + enfermeiroPadrao.getIdentificacao() + ") ---");
        List<Paciente> triagemFila = service.getFilaTriagem();
        if (triagemFila.isEmpty()) {
            System.out.println("Nenhum paciente aguardando triagem.");
            return;
        }

        Paciente proximo = triagemFila.get(0);
        System.out.println("Próximo: " + proximo.getNome() + " (senha " + proximo.getSenhaFormatada() + ")");

        String queixa = Console.lerString("Queixa principal: ");
        double temp = Console.lerDouble("Temperatura (°C): ");
        int fc = Console.lerInt("Frequência cardíaca [20-250]: ");
        int pas = Console.lerInt("PA sistólica [40-300]: ");
        int pad = Console.lerInt("PA diastólica [20-200]: ");
        int spo2 = Console.lerInt("Saturação O2 (%) [50-100]: ");
        int dor = Console.lerInt("Dor [0-10]: ");
        boolean consciente = Console.lerSimNao("Consciente? (s/n): ");

        SinaisVitais sv = new SinaisVitais(temp, fc, pas, pad, spo2, dor, consciente);
        Paciente triado = service.triarProximo(enfermeiroPadrao, queixa, sv);

        Prioridade prio = triado.getPrioridade();
        System.out.println("\n  [OK] Classificação: " + prio.getCor() + " (" + prio.getDescricao() + ", até " + prio.getTempoMaximoMin() + " min)");
    }

    private void chamarProximo() throws UpaException {
        System.out.println("\n--- ATENDIMENTO MÉDICO ---");
        Atendimento a = service.chamarProximo(medicoPadrao);
        System.out.println("Paciente chamado: " + a.getPaciente().getNome() + " (" + a.getPaciente().getSenhaFormatada() + ")");
        System.out.println("Tempo de espera: " + a.getTempoEsperaMin() + " min");
    }

    private void exibirFilas() {
        System.out.println("\n--- FILA DE TRIAGEM (Ordem de chegada) ---");
        List<Paciente> triagem = service.getFilaTriagem();
        if (triagem.isEmpty()) System.out.println("Fila vazia.");
        else triagem.forEach(p -> System.out.println(p.getSenhaFormatada() + " - " + p.getNome()));

        System.out.println("\n--- FILA DE ATENDIMENTO (Prioridade Manchester) ---");
        List<Paciente> atendimento = service.getFilaAtendimento();
        if (atendimento.isEmpty()) System.out.println("Fila vazia.");
        else atendimento.forEach(p -> System.out.println(p.getSenhaFormatada() + " - " + p.getNome() + " [" + p.getPrioridade().getCor() + "]"));
    }

    private void buscarPaciente() throws UpaException {
        System.out.println("\n1. Buscar por CPF");
        System.out.println("2. Buscar por Senha");
        int tipo = Console.lerInt("Escolha: ");

        Paciente p = null;
        if (tipo == 1) {
            String cpf = Console.lerString("CPF: ");
            p = service.buscarPacientePorCpf(cpf);
        } else if (tipo == 2) {
            int senha = Console.lerInt("Número da Senha: ");
            p = service.buscarPacientePorSenha(senha);
        }

        if (p != null) {
            System.out.println("\nPaciente Encontrado:");
            System.out.println("Nome: " + p.getNome() + " | CPF: " + p.getCpf() + " | Idade: " + p.getIdade() + " anos");
            System.out.println("Status: " + p.getStatus());
            if (p.getTriagem() != null) {
                System.out.println("Classificação: " + p.getPrioridade().getCor());
            }
        }
    }

    private void exibirRelatorios() {
        System.out.println("\n--- RELATÓRIOS ---");
        System.out.println("a) Últimos 5 atendimentos:");
        List<Atendimento> ultimos = service.relatorioUltimosAtendimentos(5);
        ultimos.forEach(a -> System.out.println(a.getPaciente().getSenhaFormatada() + " - " + a.getPaciente().getNome()));

        System.out.println("\nb) Atendimentos por tempo de espera (Maior -> Menor):");
        List<Atendimento> porTempo = service.relatorioPorTempoEspera();
        porTempo.forEach(a -> System.out.println(a.getPaciente().getNome() + " - " + a.getTempoEsperaMin() + " min"));
    }

    private void exibirEstatisticas() {
        System.out.println("\n--- ESTATÍSTICAS DO PLANTÃO ---");
        Map<Prioridade, Integer> contagem = service.contarPorPrioridade();
        for (Prioridade p : Prioridade.values()) {
            int qtd = contagem.getOrDefault(p, 0);
            String barra = "*".repeat(qtd);
            System.out.printf("%-10s | %-10s %d\n", p.getCor(), barra, qtd);
        }
        System.out.printf("Tempo médio de espera: %.1f min\n", service.tempoMedioEsperaMin());
    }
}