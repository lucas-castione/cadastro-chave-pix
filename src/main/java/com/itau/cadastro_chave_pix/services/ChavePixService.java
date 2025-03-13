package com.itau.cadastro_chave_pix.services;

import com.itau.cadastro_chave_pix.domains.ChavePix;
import com.itau.cadastro_chave_pix.domains.enums.StatusChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoPessoa;
import com.itau.cadastro_chave_pix.repositories.ChavePixRepository;
import com.itau.cadastro_chave_pix.utils.validators.ValidatorUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChavePixService {

    private final ChavePixRepository chavePixRepository;

    public ChavePixService(ChavePixRepository chavePixRepository) {
        this.chavePixRepository = chavePixRepository;
    }

    public ChavePix incluirChavePix(ChavePix chavePix) {
        validarChave(chavePix);
        chavePix.setStatus(StatusChave.ATIVA);

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

        validarChave(chavePix.getValorChave(),chavePix.getTipoChave());

    }

    public ChavePix inativarChave(UUID id) {
        ChavePix chave = chavePixRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chave não encontrada"));

        chave.setStatus(StatusChave.INATIVA);
        return chavePixRepository.save(chave);
    }

    public Optional<ChavePix> buscarPorId(UUID id) {
        return chavePixRepository.findById(id);
    }

    public List<ChavePix> listarPorConta(Integer numeroAgencia, Integer numeroConta) {
        return chavePixRepository.findByNumeroAgenciaAndNumeroConta(numeroAgencia, numeroConta);
    }


    public void validarChave(String chave, TipoChave tipo) {
        if (!ValidatorUtils.validarChave(chave, tipo)) {
            throw new IllegalArgumentException("Chave Pix inválida!");
        }
    }



}
