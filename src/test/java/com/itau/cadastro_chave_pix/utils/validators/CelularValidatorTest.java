package com.itau.cadastro_chave_pix.utils.validators;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CelularValidatorTest {

    private final CelularValidator celularValidator = new CelularValidator();

    @Test
    void deveValidarCelularCorreto() {
        assertThat(celularValidator.validarChave("+5511999999999")).isTrue();
    }

    @Test
    void deveRejeitarCelularSemCodigoPais() {
        assertThat(celularValidator.validarChave("11999999999")).isFalse();
    }

    @Test
    void deveRejeitarCelularComLetras() {
        assertThat(celularValidator.validarChave("+55abc99999999")).isFalse();
    }

    @Test
    void deveRejeitarCelularComFormatoErrado() {
        assertThat(celularValidator.validarChave("+55-11-99999-9999")).isFalse();
    }

}