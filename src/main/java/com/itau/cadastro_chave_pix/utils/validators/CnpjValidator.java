package com.itau.cadastro_chave_pix.utils.validators;

import java.util.regex.Pattern;

public class CnpjValidator implements ChavePixValidator{

    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");

    @Override
    public boolean validarChave(String chave) {
        return chave != null && CNPJ_PATTERN.matcher(chave).matches() && validarDigitosCNPJ(chave);

    }

    private boolean validarDigitosCNPJ(String cnpj) {
        int[] pesosCNPJ1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesosCNPJ2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        return validarDigitoVerificador(cnpj, pesosCNPJ1, 12) &&
                validarDigitoVerificador(cnpj, pesosCNPJ2, 13);
    }

    private boolean validarDigitoVerificador(String documento, int[] pesos, int posicao) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += (documento.charAt(i) - '0') * pesos[i];
        }
        int resto = soma % 11;
        int digitoVerificador = (resto < 2) ? 0 : (11 - resto);
        return digitoVerificador == (documento.charAt(posicao) - '0');
    }
}
