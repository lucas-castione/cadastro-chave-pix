package com.itau.cadastro_chave_pix.utils.validators;

import java.util.regex.Pattern;

public class CpfValidator  implements ChavePixValidator{

    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");

    @Override
    public boolean validarChave(String chave) {
        return chave != null && CPF_PATTERN.matcher(chave).matches() && validarDigitosCPF(chave);

    }

    private boolean validarDigitosCPF(String cpf) {
        int[] pesosCPF = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        return validarDigitoVerificador(cpf, pesosCPF, 9) &&
                validarDigitoVerificador(cpf, pesosCPF, 10);
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
