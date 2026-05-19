package com.FelipeMovio.banco.database.repository;

import com.FelipeMovio.banco.database.model.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<ContaEntity,Long> {
}
