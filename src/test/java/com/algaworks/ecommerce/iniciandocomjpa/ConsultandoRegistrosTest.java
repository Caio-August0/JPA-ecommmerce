package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.*;

public class ConsultandoRegistrosTest extends EntityManagerTest {

   @Test
   public void BuscarPorIdentificador() {
      // Buscar por REFERÊNCIA   #  por um método FIND

      /* A diferença ente o MÉTODO GETREFERENCE() para o MÉTODO FIND()
      * é que HIBERNATE não irá buscar a instância da Entidade Produto
      * na Base de dados,enquanto não usarmos alguma propiedade(atibuto)
      * da Instância de Produto.
      *
      * O SQL só será executado quando usar/operar alguma propiedade.
      *
      * Produto produto = entityManager.getReference(Produto.class,1);
      * Assertions.assertEquals("Kindle", produto.getNome());
      *
      * Neste caso, a busca na Base de Dados foi feita quando executamos o ASSERTION, pois
      * acessamos a propiedade NOME. Porém, podemos usar outros meios como uma simples impressão
      * na tela que usa o MÉTODO GET(), para acessar o valor da propiedade.EX.:
      *
      * System.out.println(produto.getNome());
      */

      /*Podemos fazer dessa maneira, já que para o  MÉTODO GETREFERENCE() precisa
      * do Id do Produto para fazer a busca, e terá somente se o MÉTODO GETID()
      * for chamado, ACESSANDO e o retornando como argumento para o GETREFERENCE()
      *
       produto = entityManager.getReference(Produto.class,produto.getId());
       *
       * "Obtenha uma instância, cujo estado pode ser obtido preguiçosamente"*/

      Produto produto = entityManager.find(Produto.class,1);
      Assertions.assertNotNull(produto);

   }
   @Test
   public void atualizarReferencia() {
      Produto produto = entityManager.find(Produto.class, 1);
      //DESFAZ mundanças
      // Como NÃO PERSISTIMOS a mudança na PROPIEDADE do Produto.
      // Sobrescrevendo as mudanças feitas na Entidade
      // O comando REFRESH irá ATUALIZAR/REVIGORAR/ A CÓPIA DO OBJETO PRODUTO,
      // buscando suas propiedades na Base de Dados.

      entityManager.refresh(produto);

      Assertions.assertEquals("Kindle", produto.getNome());
      // Confirmação que o ESTADO DA ENTIDADE FOI RESTAURADO para o mesmo que o do Banco de Dados
   }

}
