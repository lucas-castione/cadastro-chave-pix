package com.itau.cadastro_chave_pix.utils.validators;

import com.itau.cadastro_chave_pix.domains.enums.TipoChave;

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
            throw new IllegalArgumentException("Tipo de chave inválido.");
        }

        return validator.validarChave(chave);
    }


}
