package com.itau.cadastro_chave_pix.utils.validators;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CnpjValidatorTest {

    private final CnpjValidator cnpjValidator = new CnpjValidator();

    @Test
    void deveValidarCnpjCorreto() {
        assertThat(cnpjValidator.validarChave("12345678000195")).isTrue();
    }

    @Test
    void deveRejeitarCnpjComLetras() {
        assertThat(cnpjValidator.validarChave("12345abc000195")).isFalse();
    }

    @Test
    void deveRejeitarCnpjComMenosDe14Digitos() {
        assertThat(cnpjValidator.validarChave("12345678")).isFalse();
    }

    @Test
    void deveRejeitarCnpjComMaisDe14Digitos() {
        assertThat(cnpjValidator.validarChave("123456789012345")).isFalse();
    }
}