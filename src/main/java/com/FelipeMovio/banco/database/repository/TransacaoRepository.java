package com.FelipeMovio.banco.database.repository;

import com.FelipeMovio.banco.database.model.TransacoesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<TransacoesEntity, Long> {
}
