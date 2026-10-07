package br.ceub.poo.upa.modelo;

import java.time.LocalDateTime;

public class Triagem {

    private final String queixa;
    private final SinaisVitais sinaisVitais;
    private final Enfermeiro enfermeiro;
    private final Prioridade prioridade;
    private final LocalDateTime horario;

    public Triagem(String queixa, SinaisVitais sinaisVitais, Enfermeiro enfermeiro,
                   Prioridade prioridade, LocalDateTime horario) {
        this.queixa = queixa;
        this.sinaisVitais = sinaisVitais;
        this.enfermeiro = enfermeiro;
        this.prioridade = prioridade;
        this.horario = horario;
    }

    public String getQueixa() {
        return queixa;
    }

    public SinaisVitais getSinaisVitais() {
        return sinaisVitais;
    }

    public Enfermeiro getEnfermeiro() {
        return enfermeiro;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public LocalDateTime getHorario() {
        return horario;
    }
}