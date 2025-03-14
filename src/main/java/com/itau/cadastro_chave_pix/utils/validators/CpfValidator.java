package com.itau.cadastro_chave_pix.utils.validators;
import java.util.regex.Pattern;

public class CpfValidator implements ChavePixValidator {

    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");

    @Override
    public boolean validarChave(String chave) {
        return chave != null && CPF_PATTERN.matcher(chave).matches() && validarDigitosCPF(chave);
    }

    private boolean validarDigitosCPF(String cpf) {
        return validarDigitoVerificador(cpf, 9) && validarDigitoVerificador(cpf, 10);
    }

    private boolean validarDigitoVerificador(String cpf, int posicao) {
        int soma = 0;
        int peso = posicao + 1; // Começa com 10 no primeiro cálculo, 11 no segundo

        for (int i = 0; i < posicao; i++) {
            soma += (cpf.charAt(i) - '0') * peso--;
        }

        int resto = soma % 11;
        int digitoVerificador = (resto < 2) ? 0 : (11 - resto);

        return digitoVerificador == (cpf.charAt(posicao) - '0');
    }
}