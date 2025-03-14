package com.itau.cadastro_chave_pix.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String mensagem) {
        super(mensagem);
    }

}
