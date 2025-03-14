package com.itau.cadastro_chave_pix.utils.validators;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CpfValidatorTest {

    private final CpfValidator cpfValidator = new CpfValidator();

    @Test
    void deveValidarCpfCorreto() {
        assertThat(cpfValidator.validarChave("52998224725")).isTrue();
    }

    @Test
    void deveRejeitarCpfComLetras() {
        assertThat(cpfValidator.validarChave("12345abc909")).isFalse();
    }

    @Test
    void deveRejeitarCpfComMenosDe11Digitos() {
        assertThat(cpfValidator.validarChave("12345")).isFalse();
    }

    @Test
    void deveRejeitarCpfComMaisDe11Digitos() {
        assertThat(cpfValidator.validarChave("1234567890912")).isFalse();
    }
}