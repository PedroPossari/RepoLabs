package com.vemser.rest.test.produto;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.utils.GerarDadosProdutos;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoDeleteTest
{
    private final ProdutoClient produtoClient = new ProdutoClient();
    private final GerarDadosProdutos gerarDados = new GerarDadosProdutos();

    @Test
    public void testExcluindoProdutoComSucesso()
    {

        Response response = produtoClient.deletarProdutoPorID(gerarDados.gerarID());

        assertAll("response",
                () -> assertEquals(200, response.getStatusCode(), "O status code não é 200 para a exclusão do usuário"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertTrue(response.jsonPath().getString("message").contains("Registro excluído com sucesso"), "A mensagem de sucesso não está correta")
        );

    }

    @Test
    public void testExcluindoProdutoComIdInvalido()
    {

        Response response = produtoClient.deletarProdutoPorID(gerarDados.gerarDadoIdInvalido());

        assertAll("response",
                () -> assertEquals(200, response.getStatusCode(), "O status code não é 200 para a Nenhum registro excluído"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertTrue(response.jsonPath().getString("message").contains("Nenhum registro excluído"), "A mensagem de sucesso não está correta")
        );


    }

    @Test
    public void testExcluindoProdutoComIdEmBranco()
    {

        Response response = produtoClient.deletarProdutoPorID("");

        assertAll("response",
                () -> assertEquals(405, response.getStatusCode(), "O status code não é 405"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto")

        );

    }
}
