package br.ceub.poo.upa.excecao;

public class PacienteNaoEncontradoException extends UpaException {

    public PacienteNaoEncontradoException(String chave) {
        super("Paciente não encontrado: " + chave);
    }
}