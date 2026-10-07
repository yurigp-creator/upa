package br.ceub.poo.upa.excecao;

public class UpaException extends RuntimeException {

    public UpaException(String mensagem) {
        super(mensagem);
    }

    public UpaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}