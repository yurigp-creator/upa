package br.ceub.poo.upa.algoritmo;

import br.ceub.poo.upa.modelo.Paciente;
import java.util.List;

public class Busca {

    public static int buscaBinariaRecursiva(List<Paciente> lista, int senha, int inicio, int fim) {
        if (inicio > fim) {
            return -1;
        }

        int meio = inicio + (fim - inicio) / 2;
        int senhaMeio = lista.get(meio).getSenha();

        if (senhaMeio == senha) {
            return meio;
        } else if (senhaMeio > senha) {
            return buscaBinariaRecursiva(lista, senha, inicio, meio - 1);
        } else {
            return buscaBinariaRecursiva(lista, senha, meio + 1, fim);
        }
    }
}