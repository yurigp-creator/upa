package br.ceub.poo.upa.modelo;

import java.time.Duration;
import java.time.LocalDateTime;

public class Atendimento {

    private final Paciente paciente;
    private final Medico medico;
    private final LocalDateTime horarioChamada;

    public Atendimento(Paciente paciente, Medico medico, LocalDateTime horarioChamada) {
        this.paciente = paciente;
        this.medico = medico;
        this.horarioChamada = horarioChamada;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDateTime getHorarioChamada() {
        return horarioChamada;
    }

    public long getTempoEsperaMin() {
        return Duration.between(paciente.getHorarioChegada(), horarioChamada).toMinutes();
    }

    public boolean isDentroDoPrazo() {
        if (paciente.getPrioridade() == null) return true;
        return getTempoEsperaMin() <= paciente.getPrioridade().getTempoMaximoMin();
    }
}