package com.daifuku.app;

import java.util.ArrayList;
import java.util.List;

import com.daifuku.conta.ContaDAO;
import com.daifuku.conta.ContaModel;
import com.daifuku.conta.ContaService;
import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.usuario.PessoaFisica;
import com.daifuku.usuario.UsuarioDAO;
import com.daifuku.usuario.UsuarioModel;
import com.daifuku.usuario.UsuarioService;
import  com.daifuku.enums.TipoConta;

/**
 *
 *
 */
public class App 
{
    public static void main( String[] args ) throws ExcecaoNegocial {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ContaDAO  contaDAO = new ContaDAO();

        UsuarioService usuarioService = new UsuarioService(usuarioDAO);
        ContaService contaService = new ContaService(contaDAO, usuarioDAO);

        UsuarioModel usuarioPF = new PessoaFisica("nome","email@email.com","45771089095");
        Integer chaveUsuario = usuarioService.cadastrarUsuario(usuarioPF);

        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.CORRENTE);

        Integer chaveConta = contaService.cadastrar(contaCorrente);

    }
}
