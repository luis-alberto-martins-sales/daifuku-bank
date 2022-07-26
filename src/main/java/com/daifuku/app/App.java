package com.daifuku.app;

import java.util.ArrayList;
import java.util.List;

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
    public static void main( String[] args ){
        UsuarioService usuarioService = new UsuarioService(new UsuarioDAO());
        UsuarioModel usuarioPF = new PessoaFisica("nome","email","cpf");
        try {
            usuarioService.cadastrarUsuario(usuarioPF);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ;
        }
    }
}
