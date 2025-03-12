package com.itau.cadastro_chave_pix.repositories;

import com.itau.cadastro_chave_pix.domains.ChavePix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChavePixRepository extends JpaRepository<ChavePix, UUID> {

    Optional<ChavePix> findByValorChave(String valorChave);

    long countByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Integer numeroConta);

    boolean existsByValorChave(String valorChave);

    long countByNumeroConta(Integer numeroConta);

}
