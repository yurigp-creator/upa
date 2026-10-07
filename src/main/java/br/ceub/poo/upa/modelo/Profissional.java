package br.ceub.poo.upa.modelo;

import java.time.LocalDate;

public abstract class Profissional extends Pessoa {

    private final String registroConselho;

    protected Profissional(String nome, String cpf, LocalDate dataNascimento, String registroConselho) {
        super(nome, cpf, dataNascimento);
        this.registroConselho = registroConselho;
    }

    public String getRegistroConselho() {
        return registroConselho;
    }
}