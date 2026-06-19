package com.FelipeMovio.banco.database.repository;

import com.FelipeMovio.banco.database.model.TransacoesEntity;
import com.FelipeMovio.banco.database.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<TransacoesEntity, Long> {

    List<TransacoesEntity> findByPagador(UsuarioEntity pagador);

    List<TransacoesEntity> findByRecebedor(UsuarioEntity recebedor);
}
