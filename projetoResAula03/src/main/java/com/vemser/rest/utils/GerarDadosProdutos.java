package com.vemser.rest.utils;

import com.vemser.rest.client.BaseClient;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class GerarDadosProdutos extends BaseClient
{
    private final String PRODUTOS = "/produtos";

    public String gerarID()
    {
        Response response = given()
                .spec(super.set())
                .when()
                .get(PRODUTOS);
        ;
        String _id = response.jsonPath().getString("produtos[0]._id");
        return _id;
    }

    public String gerarDadoIdInvalido()
    {
        String _id = "ID_INVALIDO_123";
        return _id;
    }



}
