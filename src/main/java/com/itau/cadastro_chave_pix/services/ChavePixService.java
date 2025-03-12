package com.itau.cadastro_chave_pix.services;

import com.itau.cadastro_chave_pix.domains.ChavePix;
import com.itau.cadastro_chave_pix.domains.enums.StatusChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoPessoa;
import com.itau.cadastro_chave_pix.repositories.ChavePixRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChavePixService {

    private final ChavePixRepository chavePixRepository;

    public ChavePixService(ChavePixRepository chavePixRepository) {
        this.chavePixRepository = chavePixRepository;
    }

    public ChavePix cadastrarChave(ChavePix chavePix) {
        validarChave(chavePix);
        chavePix.setId(UUID.randomUUID());
        return chavePixRepository.save(chavePix);
    }

    private void validarChave(ChavePix chavePix) {
        if (chavePixRepository.existsByValorChave(chavePix.getValorChave())) {
            throw new IllegalArgumentException("Chave Pix já cadastrada para outro correntista.");
        }

        long quantidadeChaves = chavePixRepository.countByNumeroAgenciaAndNumeroConta(
                chavePix.getNumeroAgencia(), chavePix.getNumeroConta()
        );

        int limiteChaves = chavePix.getTipoPessoa() == TipoPessoa.FISICA ? 5 : 20;

        if (quantidadeChaves >= limiteChaves) {
            throw new RuntimeException("Limite de chaves atingido para essa conta.");
        }
    }

    public void inativarChave(UUID id) {
        ChavePix chave = chavePixRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chave não encontrada"));

        chave.setStatus(StatusChave.INATIVA);
        chavePixRepository.save(chave);
    }



}
