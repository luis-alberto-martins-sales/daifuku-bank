package com.daifuku.usuario;

import com.daifuku.databases.DatabaseUsuarios;
import com.daifuku.arquitetura.DAOInterface;


public class UsuarioDAO implements DAOInterface<UsuarioModel> {
    @Override
    public Integer cadastrarValor(UsuarioModel usuarioModel){
        return DatabaseUsuarios.getInstancia().cadastrarValor(usuarioModel);
    }

    @Override
    public UsuarioModel recuperarValor(Integer chave) {
        return DatabaseUsuarios.getInstancia().recuperarValor(chave);
    }

    @Override
    public UsuarioModel atualizarValor(Integer chave, UsuarioModel valor) {
        return DatabaseUsuarios.getInstancia().atualizarValor(chave,valor);
    }

    public UsuarioModel encontrarUsuarioPorEmail(String email){
        return DatabaseUsuarios.getInstancia().encontrarUsuarioPorEmail(email);
    }

}
