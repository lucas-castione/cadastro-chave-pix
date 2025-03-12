package com.itau.cadastro_chave_pix.services;

import com.itau.cadastro_chave_pix.domains.ChavePix;
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

        // Regra 2: Valida quantidade de chaves por conta (5 para PF, 20 para PJ)
        long quantidadeChaves = chavePixRepository.countByNumeroConta(chavePix.getNumeroConta());
        if (chavePix.getTipoChave().equals("cpf") || chavePix.getTipoChave().equals("email") || chavePix.getTipoChave().equals("celular")) {
            if (quantidadeChaves >= 5) {
                throw new IllegalArgumentException("Limite de 5 chaves atingido para essa conta.");
            }
        } else if (chavePix.getTipoChave().equals("cnpj")) {
            if (quantidadeChaves >= 20) {
                throw new IllegalArgumentException("Limite de 20 chaves atingido para essa conta.");
            }
        }
    }



}
