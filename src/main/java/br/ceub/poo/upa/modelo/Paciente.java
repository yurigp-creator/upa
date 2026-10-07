package br.ceub.poo.upa.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Paciente extends Pessoa implements Comparable<Paciente> {

    private final int senha;
    private final LocalDateTime horarioChegada;
    private StatusPaciente status;
    private Triagem triagem;

    public Paciente(String nome, String cpf, LocalDate dataNascimento, int senha, LocalDateTime horarioChegada) {
        super(nome, cpf, dataNascimento);
        this.senha = senha;
        this.horarioChegada = horarioChegada;
        this.status = StatusPaciente.AGUARDANDO_TRIAGEM;
    }

    public int getSenha() {
        return senha;
    }

    public String getSenhaFormatada() {
        return String.format("P%03d", senha);
    }

    public LocalDateTime getHorarioChegada() {
        return horarioChegada;
    }

    public StatusPaciente getStatus() {
        return status;
    }

    public Triagem getTriagem() {
        return triagem;
    }

    public Prioridade getPrioridade() {
        return (triagem != null) ? triagem.getPrioridade() : null;
    }

    public void registrarTriagem(Triagem triagem) {
        if (status != StatusPaciente.AGUARDANDO_TRIAGEM) {
            throw new IllegalStateException("Paciente já foi triado ou atendido.");
        }
        this.triagem = triagem;
        this.status = StatusPaciente.AGUARDANDO_ATENDIMENTO;
    }

    public void marcarComoAtendido() {
        if (status != StatusPaciente.AGUARDANDO_ATENDIMENTO) {
            throw new IllegalStateException("Paciente não está aguardando atendimento.");
        }
        this.status = StatusPaciente.ATENDIDO;
    }

    @Override
    public String getIdentificacao() {
        return "Paciente " + getNome() + " (senha " + getSenhaFormatada() + ")";
    }

    @Override
    public int compareTo(Paciente outro) {
        if (this.getPrioridade() == null || outro.getPrioridade() == null) {
            return Integer.compare(this.senha, outro.senha);
        }

        // 1º critério: Ordem da Prioridade (Manchester)
        int c = Integer.compare(this.getPrioridade().ordinal(), outro.getPrioridade().ordinal());
        if (c != 0) return c;

        // 2º critério: Horário de chegada (FIFO no empate)
        c = this.horarioChegada.compareTo(outro.horarioChegada);
        if (c != 0) return c;

        // 3º critério: Número da senha
        return Integer.compare(this.senha, outro.senha);
    }
}