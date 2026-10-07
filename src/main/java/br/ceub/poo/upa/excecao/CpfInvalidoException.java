package br.ceub.poo.upa.excecao;

public class CpfInvalidoException extends UpaException {

    public CpfInvalidoException(String cpf) {
        super("CPF inválido: " + cpf);
    }
}