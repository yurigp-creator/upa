package br.ceub.poo.upa.estrutura;

public interface Colecao<T> extends Iterable<T> {

    void enfileirar(T elemento);

    T desenfileirar();

    T espiar();

    boolean estaVazia();

    int getTamanho();
}