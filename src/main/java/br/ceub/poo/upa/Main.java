package br.ceub.poo.upa;

import br.ceub.poo.upa.cli.MenuCli;
import br.ceub.poo.upa.repositorio.PacienteRepositoryEmMemoria;
import br.ceub.poo.upa.servico.UpaService;
import br.ceub.poo.upa.triagem.ClassificadorManchesterSimplificado;

import java.time.Clock;

public class Main {

    public static void main(String[] args) {
        PacienteRepositoryEmMemoria repository = new PacienteRepositoryEmMemoria();
        ClassificadorManchesterSimplificado classificador = new ClassificadorManchesterSimplificado();
        UpaService service = new UpaService(repository, classificador, Clock.systemDefaultZone());
        new MenuCli(service).iniciar();
    }
}