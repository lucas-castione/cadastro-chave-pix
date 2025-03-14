package com.itau.cadastro_chave_pix.utils.validators;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class EmailValidatorTest {

    private final EmailValidator emailValidator = new EmailValidator();

    @Test
    void deveValidarEmailCorreto() {
        assertThat(emailValidator.validarChave("teste@email.com")).isTrue();
    }

    @Test
    void deveRejeitarEmailSemArroba() {
        assertThat(emailValidator.validarChave("testeemail.com")).isFalse();
    }

    @Test
    void deveRejeitarEmailComEspacos() {
        assertThat(emailValidator.validarChave("teste @email.com")).isFalse();
    }

    @Test
    void deveRejeitarEmailMaiorQue77Caracteres() {
        String emailLongo = "a".repeat(68) + "@email.com"; // 78 caracteres
        assertThat(emailValidator.validarChave(emailLongo)).isFalse();
    }
}
