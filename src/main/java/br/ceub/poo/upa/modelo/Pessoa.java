package br.ceub.poo.upa.modelo;

import java.time.LocalDate;
import java.time.Period;

public abstract class Pessoa {

    private final String nome;
    private final String cpf;
    private final LocalDate dataNascimento;

    protected Pessoa(String nome, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public abstract String getIdentificacao();
}