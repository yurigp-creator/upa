package br.ceub.poo.upa.util;

public class ValidadorCpf {

    public static boolean isValido(String cpf) {
        if (cpf == null) {
            return false;
        }

        // Remove caracteres não numéricos
        String limpo = cpf.replaceAll("\\D", "");

        if (limpo.length() != 11) {
            return false;
        }

        // Verifica CPFs com todos os dígitos iguais (ex: 111.111.111-11)
        boolean todosIguais = true;
        for (int i = 1; i < 11; i++) {
            if (limpo.charAt(i) != limpo.charAt(0)) {
                todosIguais = false;
                break;
            }
        }
        if (todosIguais) {
            return false;
        }

        try {
            // Cálculo do 1º Dígito Verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                int num = Character.getNumericValue(limpo.charAt(i));
                soma += num * (10 - i);
            }
            int resto = soma % 11;
            int dv1 = (resto < 2) ? 0 : 11 - resto;

            if (dv1 != Character.getNumericValue(limpo.charAt(9))) {
                return false;
            }

            // Cálculo do 2º Dígito Verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                int num = Character.getNumericValue(limpo.charAt(i));
                soma += num * (11 - i);
            }
            resto = soma % 11;
            int dv2 = (resto < 2) ? 0 : 11 - resto;

            return dv2 == Character.getNumericValue(limpo.charAt(10));

        } catch (Exception e) {
            return false;
        }
    }
}