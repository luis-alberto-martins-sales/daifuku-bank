package com.daifuku.usuario;

import com.daifuku.database.Database;
import com.daifuku.interfaces.CRUDInterface;

import java.util.Set;


public class UsuarioDAO implements CRUDInterface<UsuarioModel> {
    @Override
    public UsuarioModel criar(UsuarioModel usuarioModel){
        return Database.INSTANCIA.adicionarUsuario(usuarioModel);
    }

    @Override
    public UsuarioModel ler(int chave) {
        return null;
    }

    @Override
    public UsuarioModel atualizar( int chave,UsuarioModel valor) {
        return null;
    }

    @Override
    public UsuarioModel deletar(int chave) {
        return null;
    }

    public UsuarioModel encontrarUsuarioPorEmail(String email){
        return Database.INSTANCIA.encontrarUsuarioPorEmail(email);
    }

    public Set<UsuarioModel> recuperarUsuarios (){
        return Database.INSTANCIA.recuperarUsuarios ();
    }
}
