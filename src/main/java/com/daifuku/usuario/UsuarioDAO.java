package com.daifuku.usuario;

import com.daifuku.database.DatabaseUsuarios;
import com.daifuku.interfaces.CRUDInterface;

import java.util.Set;


public class UsuarioDAO implements CRUDInterface<UsuarioModel> {
    @Override
    public Integer criar(UsuarioModel usuarioModel){
        return DatabaseUsuarios.INSTANCIA.adicionarUsuario(usuarioModel);
    }

    @Override
    public UsuarioModel ler(Integer chave) {
        return null;
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
        return DatabaseUsuarios.INSTANCIA.encontrarUsuarioPorEmail(email);
    }

    public Set<UsuarioModel> recuperarUsuarios (){
        return DatabaseUsuarios.INSTANCIA.recuperarUsuarios ();
    }
}
