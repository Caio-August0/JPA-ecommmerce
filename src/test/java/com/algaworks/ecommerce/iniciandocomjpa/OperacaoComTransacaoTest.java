package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class OperacaoComTransacaoTest extends EntityManagerTest {
/*Para realizarmos manipulações no Banco de Dados
* Precisamos de uma transação. Transação é uma
* unidade de execução que acessa e manipula dado
* do Banco de Dados assegurando sua propideade ACID(Em um certo peíodo de tempo) */
// Uamtrasação é sempre delimitada, precisa ser aberta e fechada

    @Test
    public void inserirOPrimeiroObjeto() {
        Produto produto = new Produto();

        //produto.setId(2);

        // Não precisa usar o produto.setId(2);.
        // A nossa estrategia de geração de chave, fica por conta do Banco de Dados.
        // Quando definimos o valor do ID, ocorre um erro de Detached Entity

        // Detached Entity = Entidade fora de contexto, é quando
        // a entidade representa algo que possivelmente está no Banco de Dados, porém a desconhece.

        // Esse "representa algo que possivelmente está no Banco de Dados"
        // é o ID gerado pelo BD para aquela ENTIDADE, quando alteramos o ID com o setIt()
        // O EntityManeger a desconhce, pois os ID's não se correspondem. O ID da entitade
        // não é mesmo criado pelo BD para representa-la que está no Banco de Dados.

        produto.setNome("Camera Canon");
        produto.setDescricao("Boa para tirar fotos");
        produto.setPreco(new BigDecimal(5000));

        // begin e commit são o delimitadores. Toda instrução SQl
        // será executada dentro de tais delimitações
        entityManager.getTransaction().begin(); // Inicia
        entityManager.persist(produto);// Joga na memória do EntityManager para depois persistir no BD
        entityManager.getTransaction().commit();// Finaliza a Transação e Persistir

        //entityManager.getTransaction().commit(); dentro do COMMIT tem um Flush(),
        // que descarrega a Entidade no BD.
        // entityManager.flush();

        /*NÃO É POSSÍVEL FAZER UMA FLUSH NO BANCO DE DADOS SEM UMA TRANSAÇÃO
        *Força Entidade que está dentro da memória do Gerenciador
        * ir para o Banco de Dados*/

        entityManager.clear();// Limpamos a memória do EntityManager

        /* Após a EntityManager persistir o objeto no BD, o objeto
        * continua na memoria do Gerenciador de Entidade, continua
        * meio que sendo "gerenciado/rastrear pelo Gerenciador"
        * quando fazemos a confirmação o objeto foi buscado na
        * memória do Gerenciador e não no própio Banco de Dados */

        Produto produtoVerificacao = entityManager.find(Produto.class, 2);
        Assertions.assertEquals(produtoVerificacao,produto);

    }
    @Test
    public void removerObjeto() {

        Produto produtoNaoPersistido = new Produto();

        produtoNaoPersistido.setNome("Teclado RedDragon Daska");
        produtoNaoPersistido.setDescricao("Com RGB, fica ótimo para visualizar as teclas");
        produtoNaoPersistido.setPreco(new BigDecimal(237.80));

        // Antes de REMOVER é preciso BUSCAR
        Produto produtoPersistido = entityManager.find(Produto.class, 2);


        // Se o OBJETO(produtoNaoPersistido) não existe na base de dados será IGNORADO.
        // NÃO TEREMOS ERRO DE ESXECUÇÃO
        entityManager.getTransaction().begin();

            entityManager.remove(produtoNaoPersistido);// É carregado para a memória
            entityManager.remove(produtoPersistido);

        entityManager.getTransaction().commit();

        //entityManager.clear();

        /*Não precisamos usa-lo para limpar a memória
        * do EntityManager antes da ASSERTION, pois o objeto será removido do banco de dados
        * automaticamente, também será removido da memória do EntityManager, já que não
        * faz sentido ele existir em memória e não no Banco */

        Produto produtoVerificado = entityManager.find(Produto.class,2);
        Assertions.assertNull(produtoVerificado);// Confirma se houve remoção
    }

   @Test
    public void abrirEFecharTransacao() {
       Produto produto = new Produto();

       entityManager.getTransaction().begin();// Incia uma Transação

       /*entityManager.persist(produto);//persistir o produto no BD
       entityManager.remove(produto);// deletar/remover o produto no BD
       entityManager.merge(produto); */ // muda/alter o produto no BD

        entityManager.getTransaction().commit();
        // Finaliza e confirma a trasanção atual. Persiste as todo operações feito na transação no banco de dados

    }

}
