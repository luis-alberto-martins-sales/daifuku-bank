package com.daifuku.databases;

import java.util.*;

import com.daifuku.usuario.UsuarioModel;

public class DatabaseUsuarios extends Database<UsuarioModel> {

    private static final DatabaseUsuarios INSTANCIA = new DatabaseUsuarios();

    private DatabaseUsuarios() {
        if (INSTANCIA != null) {
            throw new IllegalStateException("Já instanciado.");
        }
    }

    public static DatabaseUsuarios getInstancia() {
        return INSTANCIA;
    }
    public UsuarioModel encontrarUsuarioPorEmail(String email){
        for (Integer chave:mapa.keySet()) {
            if (mapa.get(chave).getEmail().equals(email)) {
                return  clonarValor(mapa.get(chave));
            }
        }

        throw new NoSuchElementException();
    }
}
