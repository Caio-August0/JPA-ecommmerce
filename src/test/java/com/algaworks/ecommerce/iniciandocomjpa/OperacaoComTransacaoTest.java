package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class OperacaoComTransacaoTest extends EntityManagerTest {
/*Para realizarmos manipulações no Banco de Dados,
* precisamos de uma transação. Transação é uma
* UNIDADE DE EXECUÇÃO/LÓGICA DE TRABALHO (Onde se executa as instruções SQL que a compõem)
* que acessa e manipula dado do Banco de Dados,
* assegurando sua propideade ACID(Em um certo peíodo de tempo) */

// Uma transação é sempre delimitada, precisa ser aberta(begin) e fechada(end)

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
        // é o ID gerado pelo BD para aquela instância de ENTIDADE, quando alteramos o ID com o setIt()
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
        *Força instância de Entidade que está dentro da memória do Gerenciador
        * ir para o Banco de Dados*/

        entityManager.clear();// Limpamos a memória do EntityManager

        /* Após a EntityManager persistir o objeto no BD, o objeto
        * continua na memoria do Gerenciador de Entidade, continua
        * meio que sendo "gerenciado/rastrear pelo Gerenciador"
        * quando fazemos a confirmação o objeto foi buscado na
        * memória do Gerenciador e não no própio Banco de Dados */

        // Após a limpeza, ocorre o SQL no BD

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assertions.assertEquals(produtoVerificacao,produto);

    }
    @Test
    public void inserirObjetoComMerger() {

        /*Devemos PREENCHER todos os campos por ser uma instância
         * nova e nao se encontrar nem na memória do Entity Manager e
         * nem na Base de Dados */

        /* PRECISA usar o  MÉTODO SETID(). Mesmo com a estratégia de geração de chave
        * que a cargo do Banco de Dados. No FIND(),não precisamos usar o MÉTODO SETID(),
        * porém no MERGER() sim.
        *
        *
        * O MÉTODO PERSIST() CRIA UMA NOVA INSTÂNCIA E PERSISTE como a responsabilidade da
        * geração de chave fica para o Banco de Dados não precisamos definir o ID. Porém
        *
        *
        * Isso ocorre pelo fato de que o Merge() pode  inserir instância se ela não
        * existir na Base de Dados. Para isso, precisa fazer uma verificacao, usando o ID
        * porém como estamos tentando inserir um nova instância teremos que definir o ID
        *
        *
        *           Não sei ao certo
        *  */

        Produto produto = new Produto();

        produto.setId(2);
        produto.setNome("Camera Canon 2° Ed.");
        produto.setDescricao("A melhor de todos os tempos");
        produto.setPreco(new BigDecimal(350.91));

       /*Podemos persistir dados com o MÉTODO MERGE()
       * porém o OBJETO NÃO DEVE EXISTIR na Base de Dados
       * CASO CONTRÁRIO será ATUALIZADO.
       *
       * PERSISTE = INSERT
       * MERGE = UPDATE / IN SERT */
        Produto produtoMerge;
        entityManager.getTransaction().begin(); // Inicia
        produtoMerge = entityManager.merge(produto);// MERGE retorna o PERSISTE NÃO retorna
        produtoMerge.setPreco(new BigDecimal(570.45));
        entityManager.getTransaction().commit();// Finaliza a Transação e Persistir

        /* A variável de referência PRODUTOMERGE recebe uma CÓPIA DE INSTÂNCIA
        * de Produto.Gerenciada pelo Entity usamos o PRODUTOMERGE para que
        * possíveis mudanças possam ser identificadas, pois tal cópia está sendo
        * gerenciada. Se o PRODUTOMERGE, não existisse iriamos realizar mudanças
        * no OBJETO PRODUTO(não gerenciado), porém não seria idetificas já que OBJETO PRODUTO na
        * memória do Entity é uma simples cópia, não estaríamos mexendo no OBJETO GERENCIADO.
        *
        * Assim, deveríamos chamar o MERGER() mais uma vez para a
        *  */

        /*Não precisamos usar MÉTODO MERGE() novamente para realizar a atualização
        * pois o PRODUTOMERGE é uma CÓPIA de INSTÂNCIA retornada pelo MERGE()
        * e gerenciada pelo EntityManger, no qual não precisamos usar o MÉTODO MERGE()
        * novamente para fazer a atualização dos dados, pois o GERENCIADOR identifica as
        * mudanças feita na CÓPIA de INSTÂNCIA RETORNADA e atualiza a instância na memória
        * do Entity e na Base de Dados */

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assertions.assertEquals(produtoVerificacao,produto);

    }

    @Test//Produto produto = new Produto();
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

        /* Não usa-se o MÉTODO GETREFERENCE() para buscar instâncias de entidades inexistentes
         * na Base de Dados, se não uma Exceção é lançada*/

        Produto produtoVerificado = entityManager.find(Produto.class,2);
        Assertions.assertNull(produtoVerificado);// Confirma se houve remoção
    }
    @Test
    public void atualizarObjetoGerenciado1Forma() {

        /* O atributo DESCRIÇÃO terá seu valor atualizado para NULL
        * pois atualizamos a instancia da Entidade com um dos seus
        * atributos com valor de inicialização padrao.
        *
        * Quando criamos uma intância de Produto, seus atributos são iniciados
        * com os valores de inicialização padrão, desse modo devemos preencher
        * todos os atributos do atual objeto, para que quando passarmos o objeto
        * como argumento para o Método MERGER, o estado da Instância da ENTIDADE no Banco de Dados
        * não seja alterado/atualizado, para os valores de inicalização padrão,
        * assim causando a perda de dados.
        *
        * Caso não queira modificar os valores dos atribuitos, deverá repeti-los

        Nesse exemplo:

        Produto produto = new Produto();

        produto.setId(1);
        produto.setNome("Kindle PaperWhite");
        produto.setPreco(new BigDecimal(899.00)); */


        // SOLUÇÃO:

        /*Agora não precisamos preencher todos os atributos, pois recebemos uma cópia do mesmo,
        * agora, só  alteramos o que desejamos, pois o restante estará preenchido com as informações
        * dada a ele, ao invés de valores de incialização padrão*/


        Produto produto = entityManager.find(Produto.class, 1);// OBJETO GERENCIADO

        produto.setNome("Kindle PaperWhite");
        produto.setPreco(new BigDecimal(899.00));


        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoAtualizado = entityManager.getReference(Produto.class, produto.getId());
        //Assertions.assertNotNull(produtoAtualizado); Não precisamos validar o OBJETO
        // realmente existe não Base de dados, já que estamos trabalhando com um Objeto Gerenciado
        // que foi buscado do BD, e que não foi estanciado
        Assertions.assertEquals("Kindle PaperWhite",produtoAtualizado.getNome());

        //Assertions.assertEquals(produto.getNome(),produtoAtualizado.getNome());
        // Como limpamos a memória o OBJETO PRODUTO, não se encontra mais em memória para
        // realizarmos a comparação. Mesmo sendo

    }

    @Test
    public void atualizarObjetoGerenciado2Forma() {
        Produto produto = entityManager.find(Produto.class, 1);// OBJETO GERENCIADO

        entityManager.getTransaction().begin();
        produto.setNome("Kindle PaperWhite");
        produto.setPreco(new BigDecimal(899.00));
        //entityManager.merge(produto);
        entityManager.getTransaction().commit();
        /*Pelo fato que CÓPIA DA INTÂNCIA DA ENTIDADE PRODUTO ser gerenciada
        * pelo ENTITY MANAGER, ele realiza a verificação, e faz a atualização
        * automaticamente sem o MÉTODO MERGER() */

        entityManager.clear();

        Produto produtoAtualizado = entityManager.getReference(Produto.class, produto.getId());
        //Assertions.assertNotNull(produtoAtualizado); Não precisamos validar o OBJETO
        // realmente existe não Base de dados, já que estamos trabalhando com um Objeto Gerenciado
        // que foi buscado do BD, e que não foi estanciado
        Assertions.assertEquals("Kindle PaperWhite",produtoAtualizado.getNome());
        //Assertions.assertEquals(produto.getNome(),produtoAtualizado.getNome());
        // Como limpamos a memória o OBJETO PRODUTO, não se encontra mais em memória para
        // realizarmos a comparação. Mesmo sendo



    }

    @Test
    public void atualizarObjetoNaoGerenciado() {
        Produto produto = new Produto();

        produto.setId(1);
        produto.setNome("Kindle PaperWhite");
        produto.setDescricao("Ótimo para leitura");
        produto.setPreco(new BigDecimal(899.00));

        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();

        produto = entityManager.getReference(Produto.class,produto.getId());
        Assertions.assertNotNull(produto);
    }
    @Test
    public void desanexandoObjetos() {
        Produto produto = entityManager.find(Produto.class, 1);

        entityManager.detach(produto);// Desanexa na Memória

        entityManager.getTransaction().begin();
        produto.setNome("smartPhone");
        System.out.println(produto.getNome());
        entityManager.getTransaction().commit();

        //Vai buscar na BAse de Dados, pois não existe em memória

        /* A mescla(Mistura) de estado significa que as alterações feitas
         * em um objeto gerenciado pelo EntityManager serão refletidas
         * no banco de dados. Rastreia as mudanças nas entidades e as
         * sincroniza com o banco de dados quando necessário*/

        Produto produtoNaBaseDeDados = entityManager.find(Produto.class,1);
        System.out.println("Pro" + produtoNaBaseDeDados.getNome());

        /*A mesclagem de estados será realiza com após o MERGE()
        * quando for rastreado*/
        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();

        Produto produtoEmMemoria = entityManager.find(Produto.class,1);
        System.out.println("Valor na memoria: " + produtoEmMemoria.getNome());

        entityManager.clear();

        produtoNaBaseDeDados = entityManager.find(Produto.class,1);
        System.out.println("Valor na Base de Dados: " +produtoNaBaseDeDados.getNome());



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
