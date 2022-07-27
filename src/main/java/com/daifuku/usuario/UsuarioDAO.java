package com.daifuku.usuario;

import com.daifuku.databases.DatabaseUsuarios;
import com.daifuku.interfaces.DAOInterface;

import java.util.Set;


public class UsuarioDAO implements DAOInterface<UsuarioModel> {
    @Override
    public Integer criar(UsuarioModel usuarioModel){
        return DatabaseUsuarios.getInstancia().adicionarValor(usuarioModel);
    }

    @Override
    public UsuarioModel ler(Integer chave) {
        return DatabaseUsuarios.getInstancia().encontrarValor(chave);
    }

    @Override
    public UsuarioModel atualizar( Integer chave,UsuarioModel valor) {
        return null;
    }

    @Override
    public UsuarioModel deletar(Integer chave) {
        return null;
    }

    public UsuarioModel encontrarUsuarioPorEmail(String email){
        return DatabaseUsuarios.getInstancia().encontrarUsuarioPorEmail(email);
    }

    public Set<UsuarioModel> recuperarUsuarios (){
        return DatabaseUsuarios.getInstancia().recuperarValores ();
    }
}
