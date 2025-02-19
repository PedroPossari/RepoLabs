package com.vemser.rest.test.usuarios;


import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Usuario;
import com.vemser.rest.utils.GerarDadosUsuario;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;


import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;


public class UsuariosPutTest {

    private UsuarioClient usuarioClient = new UsuarioClient();
    private GerarDadosUsuario dadosDoUsuario = new GerarDadosUsuario();

    @Test
    public void testAtualizarUsuarioComSucesso()
    {

        Usuario usuario = UsuarioDataFactory.usuarioValido();

        Response response = usuarioClient.atualizarUsuarioPorID(usuario, dadosDoUsuario.gerarID());
        assertAll("response",
                () -> assertEquals(200, response.getStatusCode(), "O status code não é 200"),
                () -> assertEquals("Registro alterado com sucesso", response.jsonPath().getString("message"), "Mensagem de sucesso inválida"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto")
        );
    }

    @Test
    public void testAtualizarUsuarioComSucessoSchema()
    {

        Usuario usuario = UsuarioDataFactory.usuarioValido();

        Response response = usuarioClient.atualizarUsuarioPorID(usuario, dadosDoUsuario.gerarID());
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/atualizaUsuario.json"));
        ;
    }

    @Test
    public void testAtualizarUsuarioSemPreencherOsCampos()
    {

        Usuario usuario = UsuarioDataFactory.usuarioEmBranco();

        Response response = usuarioClient.atualizarUsuarioPorID(usuario, dadosDoUsuario.gerarID());
        assertAll("response",
                () -> assertEquals(400, response.getStatusCode(), "O status code não é 400"),
                () -> assertEquals(null, response.jsonPath().getString("message"), "Mensagem de erro inválida"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto")
        );

    }

}