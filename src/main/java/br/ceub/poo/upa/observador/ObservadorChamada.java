package br.ceub.poo.upa.observador;

import br.ceub.poo.upa.modelo.Atendimento;

@FunctionalInterface
public interface ObservadorChamada {

    void notificarChamada(Atendimento atendimento);
}