package br.ceub.poo.upa.modelo;

import br.ceub.poo.upa.excecao.SinaisVitaisInvalidosException;

public class SinaisVitais {

    private final double temperatura;
    private final int freqCardiaca;
    private final int paSistolica;
    private final int paDiastolica;
    private final int saturacaoO2;
    private final int dor;
    private final boolean consciente;

    public SinaisVitais(double temperatura, int freqCardiaca, int paSistolica, int paDiastolica,
                        int saturacaoO2, int dor, boolean consciente) throws SinaisVitaisInvalidosException {
        validar(temperatura, freqCardiaca, paSistolica, paDiastolica, saturacaoO2, dor);
        this.temperatura = temperatura;
        this.freqCardiaca = freqCardiaca;
        this.paSistolica = paSistolica;
        this.paDiastolica = paDiastolica;
        this.saturacaoO2 = saturacaoO2;
        this.dor = dor;
        this.consciente = consciente;
    }

    private void validar(double temp, int fc, int pas, int pad, int spo2, int dor) throws SinaisVitaisInvalidosException {
        if (temp < 30.0 || temp > 45.0) {
            throw new SinaisVitaisInvalidosException("Temperatura fora da faixa permitida (30.0 a 45.0 °C).");
        }
        if (fc < 20 || fc > 250) {
            throw new SinaisVitaisInvalidosException("Frequência cardíaca fora da faixa permitida (20 a 250 bpm).");
        }
        if (pas < 40 || pas > 300) {
            throw new SinaisVitaisInvalidosException("PA sistólica fora da faixa permitida (40 a 300 mmHg).");
        }
        if (pad < 20 || pad > 200) {
            throw new SinaisVitaisInvalidosException("PA diastólica fora da faixa permitida (20 a 200 mmHg).");
        }
        if (pad >= pas) {
            throw new SinaisVitaisInvalidosException("PA diastólica deve ser menor que a PA sistólica.");
        }
        if (spo2 < 50 || spo2 > 100) {
            throw new SinaisVitaisInvalidosException("Saturação de O2 fora da faixa permitida (50 a 100%).");
        }
        if (dor < 0 || dor > 10) {
            throw new SinaisVitaisInvalidosException("Escala de dor fora da faixa permitida (0 a 10).");
        }
    }

    public double getTemperatura() { return temperatura; }
    public int getFreqCardiaca() { return freqCardiaca; }
    public int getPaSistolica() { return paSistolica; }
    public int getPaDiastolica() { return paDiastolica; }
    public int getSaturacaoO2() { return saturacaoO2; }
    public int getDor() { return dor; }
    public boolean isConsciente() { return consciente; }
}