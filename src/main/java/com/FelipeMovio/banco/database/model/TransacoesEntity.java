package com.FelipeMovio.banco.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TransacoesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;

    @JoinColumn(name = "recebedor_id")
    @ManyToOne
    private UsuarioEntity recebedor;

    @JoinColumn(name = "pagador_id")
    @ManyToOne
    private UsuarioEntity pagador;

    private LocalDateTime dataHoraTransacao;

    @PrePersist
    void prePersist() {
        this.dataHoraTransacao = LocalDateTime.now();
    }
}
