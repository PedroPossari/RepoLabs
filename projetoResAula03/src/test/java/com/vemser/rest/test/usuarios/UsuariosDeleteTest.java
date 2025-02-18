package com.vemser.rest.test.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.utils.GerarDadosUsuario;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

public class UsuariosDeleteTest {

    private UsuarioClient usuarioClient = new UsuarioClient();
    private GerarDadosUsuario dadosDoUsuario = new GerarDadosUsuario();

    @Test
    public void testExcluindoUsuarioComSucesso()
    {

        Response response = usuarioClient.deletarUsuario(dadosDoUsuario.gerarID());

        assertAll("response",
                () -> assertEquals(200, response.getStatusCode(), "O status code não é 200 para a exclusão do usuário"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertTrue(response.jsonPath().getString("message").contains("Registro excluído com sucesso"), "A mensagem de sucesso não está correta")
        );

    }

    @Test
    public void testExcluindoUsuarioComSucessoSchemas()
    {

        Response response = usuarioClient.deletarUsuario(dadosDoUsuario.gerarID());

        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/deletarUsuario.json"));
        ;

    }

    @Test
    public void testExcluindoUsuarioComIdInvalido()
    {

        Response response = usuarioClient.deletarUsuario(dadosDoUsuario.gerarDadoIdInvalido());

        assertAll("response",
                () -> assertEquals(200, response.getStatusCode(), "O status code não é 200 para a Nenhum registro excluído"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertTrue(response.jsonPath().getString("message").contains("Nenhum registro excluído"), "A mensagem de sucesso não está correta")
        );


    }

    @Test
    public void testExcluindoUsuarioComIdEmBranco()
    {

        Response response = usuarioClient.deletarUsuario("");

        assertAll("response",
                () -> assertEquals(405, response.getStatusCode(), "O status code não é 405"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertTrue(response.jsonPath().getString("message")
                                .contains("Não é possível realizar DELETE em /usuarios/. Acesse http://localhost:3000 para ver as rotas disponíveis e como utilizá-las."),
                        "A mensagem de sucesso não está correta")
        );

    }

}
