package com.itau.cadastro_chave_pix.utils;

public class Validador {

    public boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");


        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0, peso = 10;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * peso--;
            }
            int primeiroDigito = 11 - (soma % 11);
            if (primeiroDigito > 9) primeiroDigito = 0;

            soma = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * peso--;
            }
            int segundoDigito = 11 - (soma % 11);
            if (segundoDigito > 9) segundoDigito = 0;

            return cpf.charAt(9) - '0' == primeiroDigito && cpf.charAt(10) - '0' == segundoDigito;
        } catch (Exception e) {
            return false;
        }

    }

}
