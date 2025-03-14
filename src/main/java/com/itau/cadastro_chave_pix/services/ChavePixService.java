package com.itau.cadastro_chave_pix.services;

import com.itau.cadastro_chave_pix.domains.ChavePix;
import com.itau.cadastro_chave_pix.domains.enums.StatusChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoPessoa;
import com.itau.cadastro_chave_pix.exceptions.NotFoundException;
import com.itau.cadastro_chave_pix.exceptions.ValidacaoException;
import com.itau.cadastro_chave_pix.repositories.ChavePixRepository;
import com.itau.cadastro_chave_pix.utils.validators.ValidatorUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
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
        if (chavePixRepository.existsByValorChave(chavePix.getValorChave())) {
            throw new ValidacaoException("Chave Pix já cadastrada para outro correntista.");
        }
        validarChave(chavePix);
        chavePix.setStatus(StatusChave.ATIVA);

        return chavePixRepository.save(chavePix);
    }

    @Transactional
    public ChavePix atualizarChave(UUID id, ChavePix chaveAtualizada) {

        ChavePix chaveAntiga = chavePixRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chave Pix não encontrada."));

        if (chaveAntiga.getStatus() != StatusChave.ATIVA) {
            throw new ValidacaoException("Chave Pix inativa. Alteração não permitida.");
        }


        if (!chaveAntiga.getValorChave().equals(chaveAtualizada.getValorChave()) ||
                !chaveAntiga.getTipoChave().equals(chaveAtualizada.getTipoChave())) {
            throw new ValidacaoException("O valor e o tipo da chave não podem ser alterados.");
        }

        validarChave(chaveAtualizada);

        chaveAntiga.setTipoConta(chaveAtualizada.getTipoConta());
        chaveAntiga.setNumeroAgencia(chaveAtualizada.getNumeroAgencia());
        chaveAntiga.setNumeroConta(chaveAtualizada.getNumeroConta());
        chaveAntiga.setNomeCorrentista(chaveAtualizada.getNomeCorrentista());
        chaveAntiga.setSobrenomeCorrentista(chaveAtualizada.getSobrenomeCorrentista());

        return chavePixRepository.save(chaveAntiga);

    }


    private void validarChave(ChavePix chavePix) {

        if (!ValidatorUtils.validarChave(chavePix)) {
            throw new ValidacaoException("erro ao validar chave pix!");
        }

        long quantidadeChaves = chavePixRepository.countByNumeroAgenciaAndNumeroConta(
                chavePix.getNumeroAgencia(), chavePix.getNumeroConta()
        );

        int limiteChaves = chavePix.getTipoPessoa() == TipoPessoa.FISICA ? 5 : 20;

        if (quantidadeChaves >= limiteChaves) {
            throw new ValidacaoException("Limite de chaves atingido para essa conta.");
        }

    }

    public ChavePix inativarChave(UUID id) {
        ChavePix chave = chavePixRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chave não encontrada"));

        if (chave.getStatus() == StatusChave.INATIVA) {
            throw new ValidacaoException("A chave já está inativa.");
        }

        chave.setStatus(StatusChave.INATIVA);

        chave.setDataHoraInativacao(LocalDateTime.now());

        return chavePixRepository.save(chave);
    }



    public Optional<ChavePix> buscarPorId(UUID id) {
        ChavePix chave = chavePixRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chave não encontrada"));

        return chavePixRepository.findById(id);
    }

    public List<ChavePix> listarPorConta(Integer numeroAgencia, Integer numeroConta) {

        List<ChavePix> chaves = chavePixRepository.findByNumeroAgenciaAndNumeroConta(numeroAgencia, numeroConta);
        if(chaves.isEmpty()){
            throw new NotFoundException("Chave não encontrada");
        }
        return chaves;
    }

    public List<ChavePix> buscarPorNomeCorrentista(String nome) {
        List<ChavePix> chaves = chavePixRepository.findByNomeCorrentista(nome);
        if(chaves.isEmpty()){
            throw new NotFoundException("nome não encontrado");
        }
        return chaves;
    }



}
