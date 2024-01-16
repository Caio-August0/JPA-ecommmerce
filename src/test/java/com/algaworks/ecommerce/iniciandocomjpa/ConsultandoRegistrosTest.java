package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.*;

public class ConsultandoRegistrosTest extends EntityManagerTest {

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
