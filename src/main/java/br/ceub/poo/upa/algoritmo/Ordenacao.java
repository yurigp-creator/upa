package br.ceub.poo.upa.algoritmo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ordenacao {

    public static <T> List<T> mergeSort(List<T> lista, Comparator<T> comparator) {
        if (lista == null || lista.size() <= 1) {
            return new ArrayList<>(lista != null ? lista : List.of());
        }

        int meio = lista.size() / 2;
        List<T> esquerda = mergeSort(lista.subList(0, meio), comparator);
        List<T> direita = mergeSort(lista.subList(meio, lista.size()), comparator);

        return intercalar(esquerda, direita, comparator);
    }

    private static <T> List<T> intercalar(List<T> esq, List<T> dir, Comparator<T> comparator) {
        List<T> resultado = new ArrayList<>();
        int i = 0, j = 0;

        while (i < esq.size() && j < dir.size()) {
            if (comparator.compare(esq.get(i), dir.get(j)) <= 0) {
                resultado.add(esq.get(i++));
            } else {
                resultado.add(dir.get(j++));
            }
        }

        while (i < esq.size()) {
            resultado.add(esq.get(i++));
        }

        while (j < dir.size()) {
            resultado.add(dir.get(j++));
        }

        return resultado;
    }
}