package com.itau.cadastro_chave_pix.utils.validators;

import java.util.regex.Pattern;

public class EmailValidator implements ChavePixValidator{

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Override
    public boolean validarChave(String chave) {
        return chave != null && EMAIL_PATTERN.matcher(chave).matches();
    }
}
