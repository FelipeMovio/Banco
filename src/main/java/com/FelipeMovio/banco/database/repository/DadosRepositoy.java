package com.FelipeMovio.banco.database.repository;

import com.FelipeMovio.banco.database.model.DadosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosRepositoy extends JpaRepository<DadosEntity,Long> {
}
