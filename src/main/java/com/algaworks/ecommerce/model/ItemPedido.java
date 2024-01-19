package com.algaworks.ecommerce.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)// Apenas incluir como argumentos atributos explicítos
@Entity
@Table(name = "Item_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincremento
    @EqualsAndHashCode.Include// Irá incluir apenas o ID como argumentos para EQUALS E HASHCODE
    private Integer id;

    @Column(name = "pedido_id")
    private Integer pedidoId;

    @Column(name = "produto_id")
    private Integer produtoId;

    @Column(name = "preco_produto")
    private BigDecimal precoProduto;

    @Column
    private Integer quantidade;

}
