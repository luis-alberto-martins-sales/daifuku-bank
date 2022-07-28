package com.daifuku.usuario;

import java.util.HashSet;
import java.util.Set;

import com.daifuku.enums.TipoUsuario;
import com.daifuku.arquitetura.ModelInterface;

public abstract class UsuarioModel implements ModelInterface {

    private String nome;
    private String email;
    private TipoUsuario tipoUsuario;
    private final Set<Integer> chavesContas = new HashSet<>();

    public UsuarioModel (String nome, String email, TipoUsuario tipoUsuario){
       
        this.nome=nome;
        this.email=email;
        this.tipoUsuario=tipoUsuario;
    }

    public String getNome(){
        return this.nome;
    }

    public String getEmail() {
        return email;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
}
