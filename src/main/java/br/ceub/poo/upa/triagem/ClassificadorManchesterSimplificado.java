package br.ceub.poo.upa.triagem;

import br.ceub.poo.upa.modelo.Prioridade;
import br.ceub.poo.upa.modelo.SinaisVitais;

public class ClassificadorManchesterSimplificado implements ClassificadorRisco {

    @Override
    public Prioridade classificar(SinaisVitais sv) {
        // 1. VERMELHO (Emergência)
        if (!sv.isConsciente() || sv.getSaturacaoO2() < 90 || sv.getFreqCardiaca() < 40
                || sv.getFreqCardiaca() > 150 || sv.getPaSistolica() < 80) {
            return Prioridade.VERMELHO;
        }

        // 2. LARANJA (Muito urgente)
        if (sv.getSaturacaoO2() <= 93 || sv.getTemperatura() >= 40.0
                || sv.getDor() >= 8 || sv.getFreqCardiaca() > 130 || sv.getPaSistolica() >= 180) {
            return Prioridade.LARANJA;
        }

        // 3. AMARELO (Urgente)
        if (sv.getTemperatura() >= 38.5 || sv.getDor() >= 5
                || sv.getFreqCardiaca() > 110 || sv.getPaSistolica() >= 160) {
            return Prioridade.AMARELO;
        }

        // 4. VERDE (Pouco urgente)
        if (sv.getTemperatura() >= 37.8 || sv.getDor() >= 1) {
            return Prioridade.VERDE;
        }

        // 5. AZUL (Não urgente)
        return Prioridade.AZUL;
    }
}