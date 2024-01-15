package com.algaworks.ecommerce.util;

import com.algaworks.ecommerce.model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class InciarUnidadeDePersistence {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Produto produto = entityManager.find(Produto.class,1);//Passo a tabela e a chave primária
        System.out.println(produto.getNome() + " R$ " + produto.getPreco());

        // Faça seus testes aqui.

        entityManager.close();
        entityManagerFactory.close();

    }
}
