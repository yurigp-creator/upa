package br.ceub.poo.upa.repositorio;

import br.ceub.poo.upa.modelo.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository {

    Optional<Paciente> buscarPorCpf(String cpf);

    void salvar(Paciente paciente);

    List<Paciente> listarTodos();
}