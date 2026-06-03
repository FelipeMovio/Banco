package com.FelipeMovio.banco.database.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dados")
public class DadosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "conta_id")
    private ContaEntity conta;

    @Column(unique = true)
    private Long numero;
    private String agencia;
    private BigDecimal saldo;
    private Boolean status;

}
