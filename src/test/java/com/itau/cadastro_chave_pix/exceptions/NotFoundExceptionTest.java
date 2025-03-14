package com.itau.cadastro_chave_pix.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {

    @Test
    public void deveLancarNotFoundExceptionComMensagemCorreta() {

        String mensagemEsperada = "Chave Pix não encontrada";
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException(mensagemEsperada);
        });
        assertEquals(mensagemEsperada, exception.getMessage());
    }
}