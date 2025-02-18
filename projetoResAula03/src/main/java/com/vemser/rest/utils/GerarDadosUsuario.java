package com.vemser.rest.utils;

import com.vemser.rest.client.BaseClient;
import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Login;
import com.vemser.rest.model.Usuario;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class GerarDadosUsuario extends BaseClient {

    private final String USUARIOS = "/usuarios";
    private UsuarioClient usuarioClient = new UsuarioClient();
    private final String LOGIN = "/login";

    public String gerarID()
    {
    Response response = given()
            .spec(super.set())
    .when()
            .get(USUARIOS);
    ;
    String _id = response.jsonPath().getString("usuarios[0]._id");
    return _id;
    }

    public String gerarDadoIdInvalido()
    {
        String _id = "ID_INVALIDO_123";
        return _id;
    }

    public String gerarToken(){
        Usuario usuario = UsuarioDataFactory.usuarioAdministrador();
        usuarioClient.cadastrarUsuarios(usuario);

        Login login = new Login();
        login.setEmail(usuario.getEmail());
        login.setPassword(usuario.getPassword());

        Response response = given()
                .spec(super.set())
                .body(login)
        .when()
                .post(LOGIN);

        String token = response.path("authorization");
        return token;

    }



}
