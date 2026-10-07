package br.ceub.poo.upa.triagem;

import br.ceub.poo.upa.modelo.Prioridade;
import br.ceub.poo.upa.modelo.SinaisVitais;

public interface ClassificadorRisco {

    Prioridade classificar(SinaisVitais sinaisVitais);
}