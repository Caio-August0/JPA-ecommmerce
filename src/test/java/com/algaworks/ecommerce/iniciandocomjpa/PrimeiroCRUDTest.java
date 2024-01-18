package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrimeiroCRUDTest extends EntityManagerTest {
    @Test
    public void inserirObjeto() {//Created
        Produto produto = new Produto();

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

       Produto produtoVerificado =  entityManager.getReference(Produto.class, produto.getId());
       Assertions.assertNotNull(produtoVerificado);
    }
    @Test
    public void consultarObjeto() {

        Produto produto = entityManager.find(Produto.class,1);
        Assertions.assertNotNull(produto);
    }
    @Test
    public void autalizarObjeto() {
        Produto produto = entityManager.find(Produto.class, 1);

        produto.setNome("Kindle 2° Geração");

        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        produto = entityManager.getReference(Produto.class, produto.getId());
        Assertions.assertEquals("Kindle 2° Geração", produto.getNome());
    }

    @Test
    public void removerObjeto() {
        Produto produto = entityManager.find(Produto.class, 1);

        entityManager.getTransaction().begin();
        entityManager.remove(produto);
        entityManager.getTransaction().commit();

        /* Não usa-se o MÉTODO GETREFERENCE() para buscar instâncias de entidades inexistentes
         * na Base de Dados, se não uma Exceção é lançada*/

        Produto produtoVerificado = entityManager.find(Produto.class, produto.getId());
        Assertions.assertNull(produtoVerificado);


    }


}
