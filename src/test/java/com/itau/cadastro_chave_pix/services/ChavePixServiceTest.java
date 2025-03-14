package com.itau.cadastro_chave_pix.services;

import com.itau.cadastro_chave_pix.domains.ChavePix;
import com.itau.cadastro_chave_pix.domains.enums.StatusChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoPessoa;
import com.itau.cadastro_chave_pix.exceptions.NotFoundException;
import com.itau.cadastro_chave_pix.exceptions.ValidacaoException;
import com.itau.cadastro_chave_pix.repositories.ChavePixRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChavePixServiceTest {

    @Mock
    private ChavePixRepository chavePixRepository;

    @InjectMocks
    private ChavePixService chavePixService;

    private ChavePix chavePix;

    private ChavePix chaveExistente;

    private UUID chaveId;

    @BeforeEach
    void setUp() {
        chavePix = new ChavePix();
        chavePix.setId(UUID.randomUUID());
        chavePix.setTipoChave(TipoChave.CPF);
        chavePix.setValorChave("49091852836");
        chavePix.setTipoConta("CORRENTE");
        chavePix.setNumeroAgencia(1234);
        chavePix.setNumeroConta(56789012);
        chavePix.setNomeCorrentista("João");
        chavePix.setSobrenomeCorrentista("Silva");
        chavePix.setTipoPessoa(TipoPessoa.FISICA);
        chavePix.setStatus(StatusChave.ATIVA);
    }


    @Test
    void deveLancarExcecaoAoIncluirChaveJaExistente() {
        when(chavePixRepository.existsByValorChave(anyString())).thenReturn(true);

        assertThrows(ValidacaoException.class, () -> chavePixService.incluirChavePix(chavePix));
    }

    @Test
    void deveInativarChavePix() {
        when(chavePixRepository.findById(any(UUID.class))).thenReturn(Optional.of(chavePix));
        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(chavePix);

        ChavePix chaveInativada = chavePixService.inativarChave(chavePix.getId());

        assertEquals(StatusChave.INATIVA, chaveInativada.getStatus());
        assertNotNull(chaveInativada.getDataHoraInativacao());
    }

    @Test
    void deveLancarExcecaoAoInativarChaveJaInativa() {
        chavePix.setStatus(StatusChave.INATIVA);
        when(chavePixRepository.findById(any(UUID.class))).thenReturn(Optional.of(chavePix));

        assertThrows(ValidacaoException.class, () -> chavePixService.inativarChave(chavePix.getId()));
    }

    @Test
    void deveBuscarChavePorId() {
        when(chavePixRepository.findById(any(UUID.class))).thenReturn(Optional.of(chavePix));

        Optional<ChavePix> resultado = chavePixService.buscarPorId(chavePix.getId());

        assertTrue(resultado.isPresent());
        assertEquals(chavePix.getId(), resultado.get().getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarChaveInexistente() {
        when(chavePixRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Optional<ChavePix> resultado = chavePixService.buscarPorId(UUID.randomUUID());

        assertFalse(resultado.isPresent());
    }

    @Test
    void deveLancarErroAoTentarCadastrarChaveExistente() {
        when(chavePixRepository.existsByValorChave(chavePix.getValorChave())).thenReturn(true);

        assertThrows(ValidacaoException.class, () -> chavePixService.incluirChavePix(chavePix));
    }

    @Test
    void deveLancarErroAoAtingirLimiteDeChaves() {
        when(chavePixRepository.countByNumeroAgenciaAndNumeroConta(anyInt(), anyInt())).thenReturn(5L);

        assertThrows(ValidacaoException.class, () -> chavePixService.incluirChavePix(chavePix));
    }

    @Test
    void deveLancarErroAoTentarAlterarValorOuTipoDaChave() {
        ChavePix chaveAtualizada = new ChavePix();
        chaveAtualizada.setValorChave("99999999999"); // Novo valor
        chaveAtualizada.setTipoChave(TipoChave.EMAIL); // Novo tipo

        when(chavePixRepository.findById(chavePix.getId())).thenReturn(Optional.of(chavePix));

        assertThrows(ValidacaoException.class, () -> chavePixService.atualizarChave(chavePix.getId(), chaveAtualizada));
    }

    @Test
    void deveLancarErroAoTentarInativarChaveJaInativa() {
        chavePix.setStatus(StatusChave.INATIVA);
        when(chavePixRepository.findById(chavePix.getId())).thenReturn(Optional.of(chavePix));

        assertThrows(ValidacaoException.class, () -> chavePixService.inativarChave(chavePix.getId()));
    }

    @Test
    void deveIncluirChavePixComSucesso() {

        when(chavePixRepository.existsByValorChave(anyString())).thenReturn(false);


        when(chavePixRepository.countByNumeroAgenciaAndNumeroConta(anyInt(), anyInt())).thenReturn(0L);

        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(chavePix);

        ChavePix resultado = chavePixService.incluirChavePix(chavePix);

        assertNotNull(resultado);
        assertEquals(StatusChave.ATIVA, resultado.getStatus());
    }

    @Test
    void deveLancarExcecaoAoCadastrarChaveJaExistente() {
        when(chavePixRepository.existsByValorChave(chavePix.getValorChave())).thenReturn(true);

        assertThrows(ValidacaoException.class, () -> chavePixService.incluirChavePix(chavePix));
    }


    @Test
    void deveInativarChavePixComSucesso() {
        when(chavePixRepository.findById(chaveId)).thenReturn(Optional.of(chavePix));
        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(chavePix);

        ChavePix resultado = chavePixService.inativarChave(chaveId);

        assertEquals(StatusChave.INATIVA, resultado.getStatus());
        assertNotNull(resultado.getDataHoraInativacao());
    }

    @Test
    void deveLancarExcecaoAoTentarInativarChaveJaInativa() {
        chavePix.setStatus(StatusChave.INATIVA);
        when(chavePixRepository.findById(chaveId)).thenReturn(Optional.of(chavePix));

        assertThrows(ValidacaoException.class, () -> chavePixService.inativarChave(chaveId));
    }

    @Test
    void deveBuscarChavePixPorId() {
        when(chavePixRepository.findById(chaveId)).thenReturn(Optional.of(chavePix));

        Optional<ChavePix> resultado = chavePixService.buscarPorId(chaveId);

        assertTrue(resultado.isPresent());
        assertEquals(chavePix, resultado.get());
    }

    @Test
    void deveLancarExcecaoAoBuscarChavePixPorIdInexistente() {
        when(chavePixRepository.findById(chaveId)).thenReturn(Optional.empty());

        Optional<ChavePix> resultado = chavePixService.buscarPorId(chaveId);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveListarChavesPorConta() {
        when(chavePixRepository.findByNumeroAgenciaAndNumeroConta(1234, 567890))
                .thenReturn(List.of(chavePix));

        List<ChavePix> resultado = chavePixService.listarPorConta(1234, 567890);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremChavesParaConta() {
        when(chavePixRepository.findByNumeroAgenciaAndNumeroConta(1234, 567890))
                .thenReturn(List.of());

        List<ChavePix> resultado = chavePixService.listarPorConta(1234, 567890);

        assertTrue(resultado.isEmpty());
    }








    @BeforeEach
    void setup() {
        chaveId = UUID.randomUUID();
        chaveExistente = new ChavePix();
        chaveExistente.setId(chaveId);
        chaveExistente.setTipoChave(TipoChave.CPF);
        chaveExistente.setValorChave("49091852836");
        chaveExistente.setTipoConta("Corrente");
        chaveExistente.setNumeroAgencia(1234);
        chaveExistente.setNumeroConta(56789);
        chaveExistente.setNomeCorrentista("João");
        chaveExistente.setTipoPessoa(TipoPessoa.FISICA);
        chaveExistente.setStatus(StatusChave.ATIVA);
    }

    @Test
    void deveAtualizarChavePixQuandoValida() {
        // Simula que a chave existe no banco
        when(chavePixRepository.findById(chaveId)).thenReturn(Optional.of(chaveExistente));
        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(chaveExistente);

        ChavePix chaveAtualizada = new ChavePix();
        chaveAtualizada.setValorChave(chaveExistente.getValorChave());
        chaveAtualizada.setTipoChave(chaveExistente.getTipoChave());
        chaveAtualizada.setTipoConta("POUPANÇA");
        chaveAtualizada.setNumeroAgencia(4321);
        chaveAtualizada.setNumeroConta(98765);
        chaveAtualizada.setTipoPessoa(TipoPessoa.FISICA);
        chaveAtualizada.setNomeCorrentista("João Silva");
        chaveAtualizada.setSobrenomeCorrentista("Silva");

        ChavePix resultado = chavePixService.atualizarChave(chaveId, chaveAtualizada);

        assertEquals("POUPANÇA", resultado.getTipoConta());
        assertEquals(4321, resultado.getNumeroAgencia());
        assertEquals(98765, resultado.getNumeroConta());
        assertEquals("João Silva", resultado.getNomeCorrentista());

        verify(chavePixRepository, times(1)).save(chaveExistente);
    }

    @Test
    void deveLancarNotFoundExceptionSeChaveNaoExistir() {
        when(chavePixRepository.findById(chaveId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> chavePixService.atualizarChave(chaveId, new ChavePix()));

        assertEquals("Chave Pix não encontrada.", exception.getMessage());
    }

    @Test
    void deveLancarValidacaoExceptionSeChaveInativa() {
        chaveExistente.setStatus(StatusChave.INATIVA);
        when(chavePixRepository.findById(chaveId)).thenReturn(Optional.of(chaveExistente));

        ValidacaoException exception = assertThrows(ValidacaoException.class,
                () -> chavePixService.atualizarChave(chaveId, new ChavePix()));

        assertEquals("Chave Pix inativa. Alteração não permitida.", exception.getMessage());
    }

    @Test
    void deveLancarValidacaoExceptionSeValorOuTipoChaveAlterado() {
        when(chavePixRepository.findById(chaveId)).thenReturn(Optional.of(chaveExistente));

        ChavePix chaveAtualizada = new ChavePix();
        chaveAtualizada.setValorChave("99999999999");
        chaveAtualizada.setTipoChave(TipoChave.EMAIL);

        ValidacaoException exception = assertThrows(ValidacaoException.class,
                () -> chavePixService.atualizarChave(chaveId, chaveAtualizada));

        assertEquals("O valor e o tipo da chave não podem ser alterados.", exception.getMessage());
    }
}