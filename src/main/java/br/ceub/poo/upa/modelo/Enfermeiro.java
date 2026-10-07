package br.ceub.poo.upa.modelo;

import java.time.LocalDate;

public class Enfermeiro extends Profissional {

    public Enfermeiro(String nome, String cpf, LocalDate dataNascimento, String registroConselho) {
        super(nome, cpf, dataNascimento, registroConselho);
    }

    @Override
    public String getIdentificacao() {
        return "Enf. " + getNome() + " — " + getRegistroConselho();
    }
}