package com.itau.cadastro_chave_pix.utils.validators;

import java.util.regex.Pattern;

public class CelularValidator implements ChavePixValidator{

    private static final Pattern CELULAR_PATTERN = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");

    @Override
    public boolean validarChave(String chave) {
        return chave != null && CELULAR_PATTERN.matcher(chave).matches();
    }
}
