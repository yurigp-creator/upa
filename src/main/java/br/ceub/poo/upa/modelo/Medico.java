package br.ceub.poo.upa.modelo;

import java.time.LocalDate;

public class Medico extends Profissional {

    private final int consultorio;

    public Medico(String nome, String cpf, LocalDate dataNascimento, String registroConselho, int consultorio) {
        super(nome, cpf, dataNascimento, registroConselho);
        this.consultorio = consultorio;
    }

    public int getConsultorio() {
        return consultorio;
    }

    @Override
    public String getIdentificacao() {
        return "Dr(a). " + getNome() + " — " + getRegistroConselho() + " (Consultório " + consultorio + ")";
    }
}