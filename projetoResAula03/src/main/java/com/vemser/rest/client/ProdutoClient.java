package com.vemser.rest.client;

import com.vemser.rest.model.Produto;
import com.vemser.rest.utils.GerarDadosUsuario;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProdutoClient extends BaseClient {
    private final String PRODUTOS = "/produtos";
    private final String PRODUTOS_ID = "/produtos/{_id}";
    private GerarDadosUsuario usuarioTest = new GerarDadosUsuario();

    public Response cadastrarProdutos(Produto produto) {

        return
                given()
                        .header("Authorization", usuarioTest.gerarToken())
                        .spec(super.set())
                        .body(produto)
                .when()
                        .post(PRODUTOS)
                ;

    }

    public Response listarTodosOsProdutos() {

        return
                given()

                        .spec(super.set())
                .when()
                        .get(PRODUTOS)
                ;

    }

    public Response listarPorNome(String nome) {

        return
                given()
                        .body(nome)
                        .spec(super.set())
                .when()
                        .get(PRODUTOS)
                ;

    }

    public Response listarProdutoPorId(String id) {

        return
                given()
                        .header("Authorization", usuarioTest.gerarToken())
                        .spec(super.set())
                        .pathParam("_id", id)
                .when()
                        .get(PRODUTOS_ID)
                ;
    }

    public Response atualizarProdutoPorID(Produto produto, String _id) {

        return given()
                .header("Authorization", usuarioTest.gerarToken())
                .spec(super.set())
                .pathParam("_id", _id)
                .body(produto)
        .when()
                .put(PRODUTOS_ID);


    }

    public Response deletarProdutoPorID(String _id) {

        return given()
                .header("Authorization", usuarioTest.gerarToken())
                .spec(super.set())
                .pathParam("_id", _id)
        .when()
                .delete(PRODUTOS_ID);


    }


}
