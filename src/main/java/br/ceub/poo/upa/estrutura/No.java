package br.ceub.poo.upa.estrutura;

final class No<T> {

    private final T valor;
    private No<T> proximo;

    No(T valor) {
        this.valor = valor;
    }

    T getValor() {
        return valor;
    }

    No<T> getProximo() {
        return proximo;
    }

    void setProximo(No<T> proximo) {
        this.proximo = proximo;
    }
}