package br.ceub.poo.upa.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public final class Console {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    private Console() {
    }

    public static String lerString(String mensagem) {
        System.out.print(mensagem);
        return SCANNER.nextLine().trim();
    }

    public static int lerInt(String mensagem) {
        while (true) {
            String entrada = lerString(mensagem);
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    public static double lerDouble(String mensagem) {
        while (true) {
            String entrada = lerString(mensagem).replace(',', '.');
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    public static LocalDate lerData(String mensagem) {
        while (true) {
            String entrada = lerString(mensagem);
            try {
                return LocalDate.parse(entrada, FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato dd/MM/aaaa.");
            }
        }
    }

    public static boolean lerSimNao(String mensagem) {
        while (true) {
            String entrada = lerString(mensagem);
            if (entrada.equalsIgnoreCase("s") || entrada.equalsIgnoreCase("sim")) {
                return true;
            }
            if (entrada.equalsIgnoreCase("n") || entrada.equalsIgnoreCase("não")
                    || entrada.equalsIgnoreCase("nao")) {
                return false;
            }
            System.out.println("Responda com s ou n.");
        }
    }
}