package br.ceub.poo.upa.estrutura;

import br.ceub.poo.upa.excecao.FilaVaziaException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Fila<T> implements Colecao<T> {

    private No<T> inicio;
    private No<T> fim;
    private int tamanho;

    @Override
    public void enfileirar(T elemento) {
        No<T> novo = new No<>(elemento);
        if (fim == null) {
            inicio = novo;
        } else {
            fim.setProximo(novo);
        }
        fim = novo;
        tamanho++;
    }

    @Override
    public T desenfileirar() {
        if (estaVazia()) {
            throw new FilaVaziaException("Fila vazia.");
        }
        T valor = inicio.getValor();
        inicio = inicio.getProximo();
        if (inicio == null) {
            fim = null;
        }
        tamanho--;
        return valor;
    }

    @Override
    public T espiar() {
        if (estaVazia()) {
            throw new FilaVaziaException("Fila vazia.");
        }
        return inicio.getValor();
    }

    @Override
    public boolean estaVazia() {
        return inicio == null;
    }

    @Override
    public int getTamanho() {
        return tamanho;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private No<T> atual = inicio;

            @Override
            public boolean hasNext() {
                return atual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T valor = atual.getValor();
                atual = atual.getProximo();
                return valor;
            }
        };
    }
}