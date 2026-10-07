package br.ceub.poo.upa.estrutura;

import br.ceub.poo.upa.excecao.FilaVaziaException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilaPrioridade<T extends Comparable<T>> implements Colecao<T> {

    private No<T> inicio;
    private int tamanho;

    public FilaPrioridade() {
        this.inicio = null;
        this.tamanho = 0;
    }

    @Override
    public void enfileirar(T elemento) {
        No<T> novo = new No<>(elemento);

        if (estaVazia() || elemento.compareTo(inicio.getValor()) < 0) {
            novo.setProximo(inicio);
            inicio = novo;
        } else {
            No<T> atual = inicio;
            while (atual.getProximo() != null && atual.getProximo().getValor().compareTo(elemento) <= 0) {
                atual = atual.getProximo();
            }
            novo.setProximo(atual.getProximo());
            atual.setProximo(novo);
        }
        tamanho++;
    }

    @Override
    public T desenfileirar() {
        if (estaVazia()) {
            throw new FilaVaziaException("Fila de prioridade vazia.");
        }
        T valor = inicio.getValor();
        inicio = inicio.getProximo();
        tamanho--;
        return valor;
    }

    @Override
    public T espiar() {
        if (estaVazia()) {
            throw new FilaVaziaException("Fila de prioridade vazia.");
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
                if (!hasNext()) throw new NoSuchElementException();
                T val = atual.getValor();
                atual = atual.getProximo();
                return val;
            }
        };
    }
}