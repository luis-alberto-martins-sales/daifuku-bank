package com.daifuku.app;

import java.util.ArrayList;
import java.util.List;

import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.usuario.PessoaFisica;
import com.daifuku.usuario.UsuarioDAO;
import com.daifuku.usuario.UsuarioModel;
import com.daifuku.usuario.UsuarioService;

/**
 *
 *
 */
public class App 
{
    public static void main( String[] args ) throws ExcecaoNegocial {
        UsuarioService usuarioService = new UsuarioService(new UsuarioDAO());
        UsuarioModel usuarioPF = new PessoaFisica("nome","email@email.com","45771089095");
        Integer chave = usuarioService.cadastrarUsuario(usuarioPF).getChave();

        usuarioService.recuperarUsuarios().forEach(System.out::println);

    }
}
