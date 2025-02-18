package com.vemser.rest.test.produto;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.Produto;
import com.vemser.rest.utils.GerarDadosProdutos;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoGetTest {

    private ProdutoClient produtoClient = new ProdutoClient();
    private GerarDadosProdutos gerarDados = new GerarDadosProdutos();

    @Test
    public void testListarTodosOsProdutos()
    {
        Response response = produtoClient.listarTodosOsProdutos();
        assertAll(
                () -> assertEquals(200, response.getStatusCode(), "O status code não é 200"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertTrue(response.jsonPath().getList("produtos").size() > 0, "A lista de usuários está vazia")
        );
    }

    @Test
    public void testListarProdutoPorNome()
    {
        Produto produto = ProdutoDataFactory.ProdutoValido();
        Response response = produtoClient.listarPorNome(produto.getNome());


        assertAll("response",
                () -> assertEquals(200, response.getStatusCode(), "O status code não é 200"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertTrue(response.jsonPath().getList("produtos").size() > 0, "Nenhum produto foi retornado na pesquisa por nome")
        );
    }

    @Test
    public void testListarProdutoPorId()
    {

            Response response =  produtoClient.listarProdutoPorId(gerarDados.gerarID());

            assertAll("response",
                    () -> assertEquals(200, response.getStatusCode(), "O status code não é 200"),
                    () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                    () -> assertTrue(response.jsonPath().getString("_id").equals(gerarDados.gerarID()), "O ID do usuário retornado não corresponde ao ID solicitado")
            );

    }

    @Test
    public void testListarProdutoPorIdInvalido()
    {

        Response response =  produtoClient.listarProdutoPorId(gerarDados.gerarDadoIdInvalido());

        assertAll("response",
                () -> assertEquals(400, response.getStatusCode(), "O status code não é 400 para ID inválido"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertTrue(response.jsonPath().getString("message").contains("Produto não encontrado"), "Mensagem de Erro não corresponde")
        );

    }

    @Test
    public void testListarProdutoPorIdEmBranco() {
        Response response = produtoClient.listarProdutoPorId("");
        assertAll("response",
                () -> assertEquals(200, response.getStatusCode(), "Ao não encontrar o id a api retorna todos os produtos"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto")

        );


    }
}
