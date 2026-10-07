package br.ceub.poo.upa.excecao;

public class PacienteJaCadastradoException extends UpaException {

    public PacienteJaCadastradoException(String cpf) {
        super("Paciente já cadastrado e ainda não atendido: " + cpf);
    }
}