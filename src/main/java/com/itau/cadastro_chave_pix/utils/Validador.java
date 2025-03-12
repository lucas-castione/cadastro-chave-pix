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

    public boolean isValidCelular(String celular) {
        celular = celular.trim();
        if (!celular.startsWith("+")) {
            return false;
        }

        String celularSemPrefixo = celular.substring(1);

        if (celularSemPrefixo.length() < 3 || celularSemPrefixo.length() > 5) {
            return false;
        }

        String codigoPais = celularSemPrefixo.substring(0, celularSemPrefixo.length() - 11);
        if (!codigoPais.matches("\\d{1,2}")) {
        }

        String ddd = celularSemPrefixo.substring(codigoPais.length(), celularSemPrefixo.length() - 9);
        if (!ddd.matches("\\d{2,3}")) {
            return false;
        }

        String numero = celularSemPrefixo.substring(codigoPais.length() + ddd.length());
        if (numero.length() != 9 || !numero.matches("\\d{9}")) {
            return false;
        }

        return true;
    }




}
