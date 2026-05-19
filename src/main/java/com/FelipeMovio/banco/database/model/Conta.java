package com.FelipeMovio.banco.database.model;

import com.FelipeMovio.banco.enums.TipoConta;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @Enumerated(EnumType.STRING)
    private TipoConta tipo;

    @OneToOne(mappedBy = "conta", cascade = CascadeType.ALL)
    private Dados dados;
}
