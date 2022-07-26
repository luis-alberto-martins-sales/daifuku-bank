package com.daifuku.usuario;

import com.daifuku.database.Database;


public class UsuarioDAO {
    public UsuarioModel adicionarUsuario(UsuarioModel usuarioModel){
        return Database.INSTANCIA.adicionarUsuario(usuarioModel);
    }

    public UsuarioModel encontrarUsuarioPorNome(String nome){
        return Database.INSTANCIA.encontrarUsuario(nome);
    }
}
