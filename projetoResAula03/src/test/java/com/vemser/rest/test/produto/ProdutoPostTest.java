package com.vemser.rest.test.produto;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.model.Produto;
import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProdutoPostTest {

    private final ProdutoClient produtoClient = new ProdutoClient();

    @ParameterizedTest
    @MethodSource("com.vemser.rest.data.provider.ProdutoProvider#produtoProvider")
    public void testCadastrarProduto(Produto produto, String descricaoTeste) {
        Response response = produtoClient.cadastrarProdutos(produto);

        if (descricaoTeste.equals("Produto válido")) {
            assertEquals(201, response.getStatusCode(), "Produto deve ser cadastrado com sucesso");
        } else if (descricaoTeste.equals("Nome do produto não pode estar vazio")) {
            assertEquals(400, response.getStatusCode(), "Produto não deve ser cadastrado sem nome");
        } else if (descricaoTeste.equals("Preço do produto não pode ser negativo")) {
            assertEquals(400, response.getStatusCode(), "Produto não deve ser cadastrado com preço negativo");
        } else {
            throw new IllegalArgumentException("Cenário de teste não reconhecido: " + descricaoTeste);
        }
    }
}
