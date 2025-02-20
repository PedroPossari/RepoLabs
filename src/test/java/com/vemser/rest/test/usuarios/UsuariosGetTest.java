package com.vemser.rest.test.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Usuario;
import com.vemser.rest.utils.GerarDadosUsuario;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

public class UsuariosGetTest {

    private UsuarioClient usuarioClient = new UsuarioClient();
    private GerarDadosUsuario dadosDoUsuario = new GerarDadosUsuario();

    @Test
    @Tag("health")
    @Feature("Usuários")
    @Severity(SeverityLevel.NORMAL)
    public void testListarUsuariosComSucesso()
    {

        Response response = usuarioClient.listarUsuarios();

        assertAll(
                () -> assertEquals(200, response.getStatusCode(), "O status code não é 200"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertTrue(response.jsonPath().getList("usuarios").size() > 0, "A lista de usuários está vazia")
        );

    }

    @Test
    @Tag("contrato")
    @Feature("Usuários")
    @Severity(SeverityLevel.CRITICAL)
    public void testListarUsuariosComSucessoSchemas()
    {

        Response response = usuarioClient.listarUsuarios();
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/listarUsuarios.json"));

    }

    @Test
    public void testListarUsuariosPorNome()
    {
        Usuario usuario = UsuarioDataFactory.usuarioValido();

        Response response = usuarioClient.listarUsuariosPorNome(usuario.getNome());

        assertAll("response",
                () -> assertEquals(200, response.getStatusCode(), "O status code não é 200"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertTrue(response.jsonPath().getList("usuarios").size() > 0, "Nenhum usuário foi retornado na pesquisa por nome")
        );

    }


    @Test
    public void testListarUsuariosPorNomeComMaisde255Caracteres()
    {

        String nome = "Pedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido Possari" +
                "Pedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido Possari" +
                "Pedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido PossariPedro Aparecido Possari";

        Response response = usuarioClient.listarUsuariosPorNome(nome);

        assertAll("response",
                () -> assertEquals(200, response.getStatusCode(), "O status code não é 200"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto")
                );

    }

    @Test
    public void testListarUsuariosPorSemPreencherOCampoNome()
    {

        Usuario usuario = UsuarioDataFactory.usuarioSemNome();

        Response response = usuarioClient.listarUsuariosPorNome(usuario.getNome());
        assertAll(
                () -> assertEquals(200, response.getStatusCode(), "O status code não é 200"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertTrue(response.jsonPath().getList("usuarios").size() > 0, "A lista de usuários deveria conter resultados mesmo com o nome vazio")
        );


    }

    @Test
    public void testListarUsuarioPorIdComSucesso()
    {
        Response response = usuarioClient.listarUsuariosPorID(dadosDoUsuario.gerarID());

        assertAll("response",
                () -> assertEquals(200, response.getStatusCode(), "O status code não é 200"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertTrue(response.jsonPath().getString("_id").equals(dadosDoUsuario.gerarID()), "O ID do usuário retornado não corresponde ao ID solicitado")
        );

    }

    @Test
    public void testListarUsuarioPorIdComSucessoSchemas()
    {

        Response response = usuarioClient.listarUsuariosPorID(dadosDoUsuario.gerarID());
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/usuariosPorId.json"))
        ;

    }

    @Test
    public void testListarUsuarioPorIdInvalido()
    {

        Response response = usuarioClient.listarUsuariosPorID(dadosDoUsuario.gerarDadoIdInvalido());
        assertAll("response",
                () -> assertEquals(400, response.getStatusCode(), "O status code não é 400 para ID inválido"),
                () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                () -> assertTrue(response.jsonPath().getString("message").contains("Usuário não encontrado"), "Mensagem de Erro não corresponde")
        );

    }

}
