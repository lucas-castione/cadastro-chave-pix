package com.itau.cadastro_chave_pix.controllers;

import com.itau.cadastro_chave_pix.domains.ChavePix;
import com.itau.cadastro_chave_pix.domains.enums.StatusChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoPessoa;
import com.itau.cadastro_chave_pix.services.ChavePixService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ChavePixController.class)
class ChavePixControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChavePixService chavePixService;

    @Test
    public void deveIncluirChavePixComSucesso() throws Exception {
        // Dados de entrada para o teste
        ChavePix chavePix = new ChavePix();
        chavePix.setValorChave("valid-key@example.com");


        ChavePix novaChave = new ChavePix();
        novaChave.setValorChave("valid-key@example.com");


        when(chavePixService.incluirChavePix(any(ChavePix.class))).thenReturn(novaChave);


        mockMvc.perform(post("/chaves-pix")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"valorChave\": \"valid-key@example.com\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valorChave").value("valid-key@example.com"));
    }
    @Test
    public void deveAtualizarChavePixComSucesso() throws Exception {

        UUID chaveId = UUID.randomUUID();


        ChavePix chavePixAtualizada = new ChavePix();
        chavePixAtualizada.setValorChave("updated-key@example.com");
        chavePixAtualizada.setTipoConta("Corrente");
        chavePixAtualizada.setNumeroAgencia(1234);
        chavePixAtualizada.setNumeroConta(56789);
        chavePixAtualizada.setNomeCorrentista("João");
        chavePixAtualizada.setSobrenomeCorrentista("Silva");
        chavePixAtualizada.setTipoPessoa(TipoPessoa.FISICA);
        chavePixAtualizada.setStatus(StatusChave.ATIVA);


        ChavePix chavePixRetorno = new ChavePix();
        chavePixRetorno.setValorChave("updated-key@example.com");


        when(chavePixService.atualizarChave(any(UUID.class), any(ChavePix.class))).thenReturn(chavePixRetorno);


        mockMvc.perform(put("/chaves-pix/{id}", chaveId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"valorChave\": \"updated-key@example.com\","
                                + "\"tipoConta\": \"Corrente\","
                                + "\"numeroAgencia\": 1234,"
                                + "\"numeroConta\": 56789,"
                                + "\"nomeCorrentista\": \"João\","
                                + "\"sobrenomeCorrentista\": \"Silva\","
                                + "\"tipoPessoa\": \"FISICA\","
                                + "\"status\": \"ATIVA\""
                                + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valorChave").value("updated-key@example.com"));
    }

    @Test
    public void deveInativarChavePixComSucesso() throws Exception {

        UUID chaveId = UUID.randomUUID();


        ChavePix chavePixInativa = new ChavePix();
        chavePixInativa.setId(chaveId);
        chavePixInativa.setStatus(StatusChave.INATIVA);

        when(chavePixService.inativarChave(chaveId)).thenReturn(chavePixInativa);


        mockMvc.perform(put("/chaves-pix/{id}/inativar", chaveId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("INATIVA"));
    }

    @Test
    public void deveRetornarChavePixQuandoEncontrada() throws Exception {
        UUID chaveId = UUID.randomUUID();


        ChavePix chavePix = new ChavePix();
        chavePix.setId(chaveId);
        chavePix.setValorChave("valid-key@example.com");


        when(chavePixService.buscarPorId(chaveId)).thenReturn(Optional.of(chavePix));


        mockMvc.perform(get("/chaves-pix/{id}", chaveId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valorChave").value("valid-key@example.com"));
    }
    @Test
    public void deveRetornarListaDeChavesPixQuandoExistirem() throws Exception {
        Integer numeroAgencia = 1234;
        Integer numeroConta = 56789;


        ChavePix chavePix1 = new ChavePix();
        chavePix1.setNumeroAgencia(numeroAgencia);
        chavePix1.setNumeroConta(numeroConta);
        chavePix1.setValorChave("key1@example.com");

        ChavePix chavePix2 = new ChavePix();
        chavePix2.setNumeroAgencia(numeroAgencia);
        chavePix2.setNumeroConta(numeroConta);
        chavePix2.setValorChave("key2@example.com");

        List<ChavePix> listaChaves = Arrays.asList(chavePix1, chavePix2);


        when(chavePixService.listarPorConta(numeroAgencia, numeroConta)).thenReturn(listaChaves);


        mockMvc.perform(get("/chaves-pix/conta")
                        .param("numeroAgencia", numeroAgencia.toString())
                        .param("numeroConta", numeroConta.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].valorChave").value("key1@example.com"))
                .andExpect(jsonPath("$[1].valorChave").value("key2@example.com"));
    }
}