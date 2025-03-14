package com.itau.cadastro_chave_pix.utils.validators;
import com.itau.cadastro_chave_pix.domains.ChavePix;
import com.itau.cadastro_chave_pix.domains.enums.StatusChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoPessoa;
import com.itau.cadastro_chave_pix.exceptions.ValidacaoException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CadastroPixValidatorTest {

    private ChavePix criarChavePixValida() {
        return new ChavePix(
                UUID.randomUUID(),
                TipoChave.EMAIL,
                "email@exemplo.com",
                "CORRENTE",
                1234,
                567890,
                "João",
                "Silva",
                TipoPessoa.FISICA,
                StatusChave.ATIVA,
                LocalDateTime.now(),
                null
        );
    }

    @Test
    void deveValidarCadastroComSucesso() {
        ChavePix chavePix = criarChavePixValida();
        assertDoesNotThrow(() -> CadastroPixValidator.validar(chavePix));
    }

    @Test
    void deveLancarExcecaoQuandoObjetoChavePixForNulo() {
        Exception exception = assertThrows(ValidacaoException.class,
                () -> CadastroPixValidator.validar(null));

        assertEquals("Objeto ChavePix não pode ser nulo.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoParaTipoContaInvalido() {
        ChavePix chavePix = criarChavePixValida();
        chavePix.setTipoConta("SALARIO"); // Tipo conta inválido

        Exception exception = assertThrows(ValidacaoException.class,
                () -> CadastroPixValidator.validar(chavePix));

        assertEquals("Tipo de conta inválido. Deve ser 'CORRENTE' ou 'POUPANÇA'.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoParaNumeroAgenciaInvalido() {
        ChavePix chavePix = criarChavePixValida();
        chavePix.setNumeroAgencia(123); // Agência inválida (apenas 3 dígitos)

        Exception exception = assertThrows(ValidacaoException.class,
                () -> CadastroPixValidator.validar(chavePix));

        assertEquals("Número da agência deve ter exatamente 4 dígitos.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoParaNumeroContaInvalido() {
        ChavePix chavePix = criarChavePixValida();
        chavePix.setNumeroConta(123456789); // Conta inválida (> 8 dígitos)

        Exception exception = assertThrows(ValidacaoException.class,
                () -> CadastroPixValidator.validar(chavePix));

        assertEquals("Número da conta deve ter no máximo 8 dígitos.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoParaNomeCorrentistaInvalido() {
        ChavePix chavePix = criarChavePixValida();
        chavePix.setNomeCorrentista(""); // Nome inválido (vazio)

        Exception exception = assertThrows(ValidacaoException.class,
                () -> CadastroPixValidator.validar(chavePix));

        assertEquals("Nome do correntista é obrigatório e deve ter no máximo 30 caracteres.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoParaSobrenomeCorrentistaMuitoLongo() {
        ChavePix chavePix = criarChavePixValida();
        chavePix.setSobrenomeCorrentista("Silva".repeat(12)); // Sobrenome inválido (> 45 caracteres)

        Exception exception = assertThrows(ValidacaoException.class,
                () -> CadastroPixValidator.validar(chavePix));

        assertEquals("Sobrenome do correntista deve ter no máximo 45 caracteres.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoParaTipoPessoaNulo() {
        ChavePix chavePix = criarChavePixValida();
        chavePix.setTipoPessoa(null); // Tipo de pessoa inválido

        Exception exception = assertThrows(ValidacaoException.class,
                () -> CadastroPixValidator.validar(chavePix));

        assertEquals("Tipo de pessoa não pode ser nulo.", exception.getMessage());
    }
}