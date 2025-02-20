package com.vemser.rest.test.login;


import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.Login;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    private LoginClient loginClient = new LoginClient();

        @Test
        public void testLogarComSucesso()
        {

            Login login = LoginDataFactory.loginValido();

            Response response = loginClient.logarNoSistema(login);

            assertAll("response",
                    () -> assertEquals(200, response.getStatusCode(), "O status code não é 200"),
                    () -> assertEquals("application/json; charset=utf-8", response.header("Content-Type"), "O Content-Type está incorreto"),
                    () -> assertTrue(response.jsonPath().getString("authorization") != null, "O token não foi retornado na resposta"),
                    () -> assertTrue(response.jsonPath().getString("message").contains("Login realizado com sucesso"), "A mensagem de sucesso não está correta")
            );

        }

    @Test
    @Tag("contrato")
    @Feature("Autenticação")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogarComSucessoSchemas()
    {
        Login login = LoginDataFactory.loginValido();

        Response response = loginClient.logarNoSistema(login);

        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/login.json"))
                ;

    }

    @Test
    public void testLogarSenhaEmBranco()
    {
        Login login = LoginDataFactory.loginSemSenha();

        Response response = loginClient.logarNoSistemaComSenhaEmBranco(login);


        assertAll("response",
                () -> assertEquals(400, response.getStatusCode(), "O status code não é 400"),
                () -> assertTrue(response.jsonPath().getString("password").contains("password não pode ficar em branco"), "A mensagem de erro não está correta")
        );
    }

    @Test
    public void testLogarComEmailEmBranco()
    {

        Login login = LoginDataFactory.loginSemEmail();

        Response response = loginClient.logarNoSistemaComEmailEmBranco(login);

        assertAll("response",
                () -> assertEquals(400, response.getStatusCode(), "O status code não é 400"),
                () -> assertTrue(response.jsonPath().getString("email").contains("email não pode ficar em branco"), "A mensagem de erro não está correta")
        );

    }
}

