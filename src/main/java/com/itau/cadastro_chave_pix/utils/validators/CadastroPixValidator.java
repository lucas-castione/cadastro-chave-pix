package com.itau.cadastro_chave_pix.utils.validators;

import com.itau.cadastro_chave_pix.domains.ChavePix;
import com.itau.cadastro_chave_pix.domains.enums.TipoPessoa;
import com.itau.cadastro_chave_pix.exceptions.ValidacaoException;

public class CadastroPixValidator {

    public static void validar(ChavePix chavePix) {
        if (chavePix == null) {
            throw new ValidacaoException("Objeto ChavePix não pode ser nulo.");
        }

        validarTipoConta(chavePix.getTipoConta());
        validarNumeroAgencia(chavePix.getNumeroAgencia());
        validarNumeroConta(chavePix.getNumeroConta());
        validarNomeCorrentista(chavePix.getNomeCorrentista());
        validarSobrenomeCorrentista(chavePix.getSobrenomeCorrentista());
        validarTipoPessoa(chavePix.getTipoPessoa());
    }

    private static void validarTipoConta(String tipoConta) {
        if (tipoConta == null || !(tipoConta.equals("CORRENTE") || tipoConta.equals("POUPANÇA"))) {
            throw new ValidacaoException("Tipo de conta inválido. Deve ser 'CORRENTE' ou 'POUPANÇA'.");
        }
    }

    private static void validarNumeroAgencia(Integer numeroAgencia) {
        if (numeroAgencia == null || String.valueOf(numeroAgencia).length() != 4) {
            throw new ValidacaoException("Número da agência deve ter exatamente 4 dígitos.");
        }
    }

    private static void validarNumeroConta(Integer numeroConta) {
        if (numeroConta == null || String.valueOf(numeroConta).length() > 8) {
            throw new ValidacaoException("Número da conta deve ter no máximo 8 dígitos.");
        }
    }

    private static void validarNomeCorrentista(String nomeCorrentista) {
        if (nomeCorrentista == null || nomeCorrentista.trim().isEmpty() || nomeCorrentista.length() > 30) {
            throw new ValidacaoException("Nome do correntista é obrigatório e deve ter no máximo 30 caracteres.");
        }
    }

    private static void validarSobrenomeCorrentista(String sobrenomeCorrentista) {
        if (sobrenomeCorrentista != null && sobrenomeCorrentista.length() > 45) {
            throw new ValidacaoException("Sobrenome do correntista deve ter no máximo 45 caracteres.");
        }
    }

    private static void validarTipoPessoa(TipoPessoa tipoPessoa) {
        if (tipoPessoa == null) {
            throw new ValidacaoException("Tipo de pessoa não pode ser nulo.");
        }
    }
}
