package br.ceub.poo.upa.estrutura;

import br.ceub.poo.upa.excecao.FilaVaziaException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Pilha<T> implements Colecao<T> {

    private No<T> topo;
    private int tamanho;

    public Pilha() {
        this.topo = null;
        this.tamanho = 0;
    }

    public void empilhar(T elemento) {
        No<T> novo = new No<>(elemento);
        novo.setProximo(topo);
        topo = novo;
        tamanho++;
    }

    public T desempilhar() {
        if (estaVazia()) {
            throw new FilaVaziaException("Pilha vazia.");
        }
        T valor = topo.getValor();
        topo = topo.getProximo();
        tamanho--;
        return valor;
    }

    @Override
    public void enfileirar(T elemento) {
        empilhar(elemento);
    }

    @Override
    public T desenfileirar() {
        return desempilhar();
    }

    @Override
    public T espiar() {
        if (estaVazia()) {
            throw new FilaVaziaException("Pilha vazia.");
        }
        return topo.getValor();
    }

    @Override
    public boolean estaVazia() {
        return topo == null;
    }

    @Override
    public int getTamanho() {
        return tamanho;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private No<T> atual = topo;

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