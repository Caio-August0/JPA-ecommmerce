package com.algaworks.ecommerce.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.Objects;


@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Apenas incluir como arqgumentos os atributos ecplicítos
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include// Peço para incluir
    private Integer id;

    @Column
    private String nome;

    @Column(name = "sexo_cliente")
    private SexoCliente sexoCliente;

}
