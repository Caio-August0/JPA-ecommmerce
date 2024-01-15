package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class ConsultandoRegistrosTest {
   private static EntityManagerFactory entityManagerFactory;
   private EntityManager entityManager;

   @BeforeAll // Será executado ANTES DE TODOS os teste
   public static void setUpBeforeClass() { // método callback
      entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
   }
   @AfterAll // Será executado SOMENTE DEPOIS DE TODOS os teste
   public static void tearDownAfterClass() {
      entityManagerFactory.close();
   }
   @BeforeEach // vai ser executado ANTES DE CADA UM dos teste
   public void setUp() {
      entityManager = entityManagerFactory.createEntityManager();
   }
   @AfterEach // vai ser executado DEPOIS DE CADA UM dos teste
   public void tearDown() {

   }
   @Test
   public void BuscarPorIdentificador() {

      Produto produto = entityManager.find(Produto.class,1);
      Assertions.assertNotNull(produto);

      /*
      Produto produto = entityManager.getReference(Produto.class,1);
      Assertions.assertEquals("Kindle", produto.getNome());
      */
   }
   @Test
   public void atualizarReferencia() {
      Produto produto = entityManager.find(Produto.class, 1);
      produto.setNome("Microsoft Samson");// Mudamos a PROPIEDADE da CÓPIA DO OBJETO PRODUTO

      // Irá VOLTAR o ESTADO da Entidade que está no Banco de Dados
      // Como não PERSISTIMOS a mudança na PROPIEDADE do Produto.
      // O comando REFRESH irá ATUALIZAR A CÓPIA DO OBJETO PRODUTO, buscando suas propiedades.
      // Sobrescrevendo as mudanças feitas na Entidade
      entityManager.refresh(produto);

      Assertions.assertEquals("Kindle", produto.getNome());
      // Confirmação que o ESTADO DA ENTIDADE FOI RESTAURADO para o mesmo que o do Banco de Dados
   }

}
