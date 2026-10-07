package br.ceub.poo.upa.modelo;

public enum Prioridade {
    VERMELHO("Vermelho", "Emergência", 0),
    LARANJA("Laranja", "Muito urgente", 10),
    AMARELO("Amarelo", "Urgente", 60),
    VERDE("Verde", "Pouco urgente", 120),
    AZUL("Azul", "Não urgente", 240);

    private final String cor;
    private final String descricao;
    private final int tempoMaximoMin;

    Prioridade(String cor, String descricao, int tempoMaximoMin) {
        this.cor = cor;
        this.descricao = descricao;
        this.tempoMaximoMin = tempoMaximoMin;
    }

    public String getCor() {
        return cor;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getTempoMaximoMin() {
        return tempoMaximoMin;
    }
}