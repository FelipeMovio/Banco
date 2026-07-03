package com.FelipeMovio.banco.database.repository;

import com.FelipeMovio.banco.database.model.PixKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PixKeyRepository extends JpaRepository<PixKeyEntity, Long> {

    Optional<PixKeyEntity> findByChave(String chave);

    boolean existsByChave(String chave);

    List<PixKeyEntity> findAllByContaId(Long contaId);

}
