package com.vemser.rest.client;

import com.vemser.rest.model.Usuario;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class UsuarioClient extends BaseClient  {

    private final String USUARIOS = "/usuarios";
    private final String USUARIOS_ID = "/usuarios/{_id}";

    public Response cadastrarUsuarios(Usuario usuario) {

        return
                given()
                        .spec(super.set())
                        .body(usuario)
                .when()
                        .post(USUARIOS)
                ;

    }

    public Response listarUsuarios() {

        return
                given()
                        .spec(super.set())
                .when()
                        .get(USUARIOS)
                ;

    }

    public Response listarUsuariosPorNome(String nome) {

        return
                given()
                        .body(nome)
                        .spec(super.set())
                .when()
                        .get(USUARIOS)
                ;

    }

    public Response listarUsuariosPorID(String id) {

        return
                given()
                        .spec(super.set())
                        .pathParam("_id", id)
                .when()
                        .get(USUARIOS_ID)
                ;

    }

    public Response atualizarUsuarioPorID(Usuario usuario, String _id) {

        return given()
                .spec(super.set())
                .pathParam("_id", _id)
                .body(usuario)
        .when()
                .put(USUARIOS_ID);


    }

    public Response deletarUsuario(String _id) {

        return given()
                .spec(super.set())
                .pathParam("_id", _id)
        .when()
                .delete(USUARIOS_ID);


    }




}