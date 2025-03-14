package com.itau.cadastro_chave_pix.domains;

import com.itau.cadastro_chave_pix.domains.enums.StatusChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoPessoa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChavePixTest {
    @Test
    void devePreencherDataHoraInclusaoEStatusAoPersistir() {
        // Criando um objeto sem valores definidos para dataHoraInclusao e status
        ChavePix chavePix = new ChavePix();
        chavePix.setTipoChave(TipoChave.CPF);
        chavePix.setValorChave("12345678900");
        chavePix.setTipoConta("Corrente");
        chavePix.setNumeroAgencia(1234);
        chavePix.setNumeroConta(56789);
        chavePix.setNomeCorrentista("João");
        chavePix.setTipoPessoa(TipoPessoa.FISICA);

        chavePix.prePersist();


        assertNotNull(chavePix.getDataHoraInclusao(), "A data de inclusão não foi preenchida!");

        assertEquals(StatusChave.ATIVA, chavePix.getStatus(), "O status da chave Pix deveria ser ATIVA!");
    }



}