package com.itau.cadastro_chave_pix.utils.validators;

import com.itau.cadastro_chave_pix.domains.enums.TipoChave;
import com.itau.cadastro_chave_pix.exceptions.ValidacaoException;

import java.util.EnumMap;
import java.util.Map;

public class ValidatorUtils {
    private static final Map<TipoChave, ChavePixValidator> VALIDATORS = new EnumMap<>(TipoChave.class);

    static {
        VALIDATORS.put(TipoChave.CPF, new CpfValidator());
        VALIDATORS.put(TipoChave.CNPJ, new CnpjValidator());
        VALIDATORS.put(TipoChave.EMAIL, new EmailValidator());
        VALIDATORS.put(TipoChave.CELULAR, new CelularValidator());
    }

    public static boolean validarChave(String chave, TipoChave tipo) {
        ChavePixValidator validator = VALIDATORS.get(tipo);

        if (validator == null) {
            throw new ValidacaoException("Tipo de chave inválido.");
        }
        if (!validator.validarChave(chave)) {
            throw new ValidacaoException("A chave " + chave + " não é válida para o tipo " + tipo);
        }

        return validator.validarChave(chave);
    }


}
