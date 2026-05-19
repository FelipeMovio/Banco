package com.FelipeMovio.banco.database.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Dados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    private Long numero;
    private String agencia;
    private Double saldo;
    private Boolean status;

}
