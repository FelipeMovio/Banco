package com.FelipeMovio.banco.database.repository;

import com.FelipeMovio.banco.database.model.PixKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PixKeyRepository extends JpaRepository<PixKeyEntity, Long> {

    Optional<PixKeyEntity> findByChave(String chave);

    Optional<PixKeyEntity> findByIdAndAtivaTrue(Long id);

    Optional<PixKeyEntity> findByChaveAndAtivaTrue(String chave);

    boolean existsByChave(String chave);

    List<PixKeyEntity> findAllByContaId(Long contaId);

    List<PixKeyEntity> findAllByContaIdAndAtivaTrue(Long contaId);

    long countByContaIdAndAtivaTrue(Long contaId);

}
