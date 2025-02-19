package com.vemser.rest.test.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Usuario;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

public class UsuariosPostTest {

    private UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    public void testCadastrarUsuarioComSucesso()
    {
        Usuario usuario = UsuarioDataFactory.usuarioValido();

        Response response = usuarioClient.cadastrarUsuarios(usuario);

        assertAll("response",
                () -> assertEquals(201, response.getStatusCode(), "O status code não é 201"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertNotNull(response.jsonPath().getString("_id"), "O ID do usuário não foi retornado"),
                () -> assertEquals("Cadastro realizado com sucesso", response.jsonPath().getString("message"), "Mensagem de sucesso inválida")

        );

    }

    @Test
    public void testCadastrarUsuarioComSucessoSchemas()
    {

        Usuario usuario = UsuarioDataFactory.usuarioValido();

        Response response = usuarioClient.cadastrarUsuarios(usuario);
        response.then()
        .body(matchesJsonSchemaInClasspath("schemas/cadastrarUsuario.json"))
        ;


    }


    @Test
    public void testCadastrarUsuarioSemPreencherOCampoNome()
    {

        Usuario usuario = UsuarioDataFactory.usuarioSemNome();

        Response response = usuarioClient.cadastrarUsuarios(usuario);
        assertAll("response",
                () -> assertEquals(400, response.getStatusCode(), "O status code não é 400"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertEquals("nome não pode ficar em branco", response.jsonPath().getString("nome"), "Mensagem de erro inválida")
        );
    }

    @Test
    public void testCadastrarUsuarioSemSenha()
    {

        Usuario usuario = UsuarioDataFactory.usuarioSemSenha();

        Response response = usuarioClient.cadastrarUsuarios(usuario);
        assertAll("response",
                () -> assertEquals(400, response.getStatusCode(), "O status code não é 400"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertEquals("password não pode ficar em branco", response.jsonPath().getString("password"), "Mensagem de erro inválida")
        );
    }

}
