package com.algaworks.ecommerce.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)// Apenas incluir como argumentos atributos explicítos
@Entity
@Table(name = "Pagamento_boleto")
public class PagamentoBoleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincremento
    @EqualsAndHashCode.Include// Irá incluir apenas o ID como argumentos para EQUALS E HASHCODE
    private Integer id;

    @Column(name = "pedido_id")
    private Integer pedidoId;

    @Column(name = "codigo_de_barras")
    private Integer codigoDeBarras;

    @Column(name = "status_pagamento")
    private StatusPagamento statusPagamento;

}
