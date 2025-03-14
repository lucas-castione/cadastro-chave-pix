package com.itau.cadastro_chave_pix.utils.validators;

import com.itau.cadastro_chave_pix.domains.ChavePix;
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

    public static boolean validarChave(ChavePix chavePix) {
        CadastroPixValidator.validar(chavePix);
        ChavePixValidator validator = VALIDATORS.get(chavePix.getTipoChave());

        if (validator == null) {
            throw new ValidacaoException("Tipo de chave inválido.");
        }
        if (!validator.validarChave(chavePix.getValorChave())) {
            throw new ValidacaoException("A chave " + chavePix.getValorChave() + " não é válida para o tipo " + chavePix.getTipoChave());
        }

        return validator.validarChave(chavePix.getValorChave());
    }




}
