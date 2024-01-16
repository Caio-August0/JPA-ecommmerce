package com.algaworks.ecommerce;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class EntityManagerTest {
   protected static EntityManagerFactory entityManagerFactory;
   protected  EntityManager entityManager;

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

}
