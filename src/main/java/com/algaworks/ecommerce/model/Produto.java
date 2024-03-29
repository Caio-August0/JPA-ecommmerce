package com.algaworks.ecommerce.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)// Apenas incluir como argumentos atributos explicítos
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincremento
    @EqualsAndHashCode.Include// Irá incluir apenas o ID como argumentos para EQUALS E HASHCODE
    private Integer id;

    @Column
    private String nome;

    @Column
    private String descricao;

    @Column
    private BigDecimal preco;


}
