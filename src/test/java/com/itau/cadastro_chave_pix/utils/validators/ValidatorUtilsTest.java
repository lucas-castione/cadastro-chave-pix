package com.itau.cadastro_chave_pix.utils.validators;

import com.itau.cadastro_chave_pix.domains.ChavePix;
import com.itau.cadastro_chave_pix.domains.enums.TipoChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoPessoa;
import com.itau.cadastro_chave_pix.exceptions.ValidacaoException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorUtilsTest {

    private ChavePix criarChavePixValida(TipoChave tipoChave, String valorChave) {
        return new ChavePix(
                UUID.randomUUID(),
                tipoChave,
                valorChave,
                "CORRENTE",
                1234,
                567890,
                "João",
                "Silva",
                TipoPessoa.FISICA,
                null,
                LocalDateTime.now(),
                null
        );
    }

    @Test
    void deveValidarChaveCpfValida() {
        ChavePix chavePix = criarChavePixValida(TipoChave.CPF, "12345678909");
        assertTrue(ValidatorUtils.validarChave(chavePix));
    }

    @Test
    void deveRejeitarCpfInvalido() {
        ChavePix chavePix = criarChavePixValida(TipoChave.CPF, "12345678900");
        assertThrows(ValidacaoException.class, () -> ValidatorUtils.validarChave(chavePix));
    }

    @Test
    void deveValidarChaveEmailValida() {
        ChavePix chavePix = criarChavePixValida(TipoChave.EMAIL, "teste@email.com");
        assertTrue(ValidatorUtils.validarChave(chavePix));
    }

    @Test
    void deveRejeitarEmailInvalido() {
        ChavePix chavePix = criarChavePixValida(TipoChave.EMAIL, "testeemail.com");
        assertThrows(ValidacaoException.class, () -> ValidatorUtils.validarChave(chavePix));
    }

    @Test
    void deveRejeitarTipoChaveInvalido() {
        ChavePix chavePix = criarChavePixValida(null, "qualquerCoisa");
        assertThrows(ValidacaoException.class, () -> ValidatorUtils.validarChave(chavePix));
    }

    @Test
    void deveRejeitarChaveComValorInvalido() {
        ChavePix chavePix = criarChavePixValida(TipoChave.CELULAR, "119999999"); // Número inválido
        assertThrows(ValidacaoException.class, () -> ValidatorUtils.validarChave(chavePix));
    }
}