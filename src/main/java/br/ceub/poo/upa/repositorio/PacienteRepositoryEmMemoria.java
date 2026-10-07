package br.ceub.poo.upa.repositorio;

import br.ceub.poo.upa.modelo.Paciente;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PacienteRepositoryEmMemoria implements PacienteRepository {

    private final Map<String, Paciente> pacientesPorCpf = new LinkedHashMap<>();

    @Override
    public Optional<Paciente> buscarPorCpf(String cpf) {
        return Optional.ofNullable(pacientesPorCpf.get(cpf));
    }

    @Override
    public void salvar(Paciente paciente) {
        pacientesPorCpf.put(paciente.getCpf(), paciente);
    }

    @Override
    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientesPorCpf.values());
    }
}