package com.vemser.rest.data.factory;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.model.Login;
import com.vemser.rest.model.Usuario;
import net.datafaker.Faker;

import java.util.Locale;

public class LoginDataFactory
{
    static Faker faker = new Faker(new Locale("PT-BR"));
    public static UsuarioClient usuarioClient = new UsuarioClient();

    public static Login loginValido() {

        Usuario usuario = UsuarioDataFactory.usuarioValido();
        usuarioClient.cadastrarUsuarios(usuario);
        Login login = new Login();
        login.setEmail(usuario.getEmail());
        login.setPassword(usuario.getPassword());

        return login;
    }

    public static Login loginSemEmail() {

        Usuario usuario = UsuarioDataFactory.usuarioValido();
        usuarioClient.cadastrarUsuarios(usuario);
        Login login = new Login();
        login.setEmail("");
        login.setPassword(usuario.getPassword());

        return login;
    }

    public static Login loginSemSenha() {

        Usuario usuario = UsuarioDataFactory.usuarioValido();
        usuarioClient.cadastrarUsuarios(usuario);
        Login login = new Login();
        login.setEmail(usuario.getEmail());
        login.setPassword("");

        return login;
    }
}
