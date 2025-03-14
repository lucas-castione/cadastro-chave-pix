package com.itau.cadastro_chave_pix.exceptions;

import com.itau.cadastro_chave_pix.domains.ChavePix;
import com.itau.cadastro_chave_pix.services.ChavePixService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.doThrow;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChavePixService chavePixService;

    @Test
    public void deveRetornar404QuandoNotFoundExceptionForLançada() throws Exception {

        doThrow(new NotFoundException("Chave Pix não encontrada")).when(chavePixService).incluirChavePix(any(ChavePix.class));


    }


}