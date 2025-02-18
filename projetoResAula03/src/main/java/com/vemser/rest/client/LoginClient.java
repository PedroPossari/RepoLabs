package com.vemser.rest.client;

import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Login;
import com.vemser.rest.model.Usuario;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginClient extends BaseClient {

    private UsuarioClient usuarioClient = new UsuarioClient();
    private final String LOGIN = "/login";

    public Response logarNoSistema(Login login) {

        return
                given()
                        .spec(super.set())
                        .body(login)
                .when()
                        .post(LOGIN);


    }

    public Response logarNoSistemaComSenhaEmBranco(Login login) {

        return
                given()
                        .spec(super.set())
                        .body(login)
                .when()
                        .post(LOGIN);

    }

    public Response logarNoSistemaComEmailEmBranco(Login login) {

        return
                given()
                        .spec(super.set())
                        .body(login)
                .when()
                        .post(LOGIN);

    }

}
