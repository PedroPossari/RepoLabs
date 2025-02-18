package com.vemser.rest.test.produto;


import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.Produto;
import com.vemser.rest.utils.GerarDadosProdutos;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProdutoPutTest
{
    private final ProdutoClient produtoClient = new ProdutoClient();
    private final GerarDadosProdutos gerarDados = new GerarDadosProdutos();

    @Test
    public void testAtualizarProdutoComSucesso()
    {

        Produto produto = ProdutoDataFactory.ProdutoValido();

        Response response = produtoClient.atualizarProdutoPorID(produto, gerarDados.gerarID());
        assertAll("response",
                () -> assertEquals(200, response.getStatusCode(), "O status code não é 200"),
                () -> assertEquals("Registro alterado com sucesso", response.jsonPath().getString("message"), "Mensagem de sucesso inválida"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto")
        );
    }

    @Test
    public void testAtualizarProdutoSemPreencherOsCampos()
    {

        Produto produto = ProdutoDataFactory.ProdutoEmBranco();

        Response response = produtoClient.atualizarProdutoPorID(produto, gerarDados.gerarID());
        assertAll("response",
                () -> assertEquals(400, response.getStatusCode(), "O status code não é 400"),
                () -> assertEquals(null, response.jsonPath().getString("message"), "Mensagem de erro inválida"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto")
        );

    }

    @Test
    public void testAtualizarProdutoComNomeFixo()
    {

        Produto produto = ProdutoDataFactory.ProdutoComNomeFixc();

        Response response = produtoClient.atualizarProdutoPorID(produto, gerarDados.gerarID());
        assertAll("response",
                () -> assertEquals(400, response.getStatusCode(), "O status code não é 400"),
                () -> assertEquals(null, response.jsonPath().getString("message"), "Mensagem de erro inválida"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto")
        );

    }




}
