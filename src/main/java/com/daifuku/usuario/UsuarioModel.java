package com.daifuku.usuario;

import java.util.Set;

import com.daifuku.enums.TipoUsuario;
import com.daifuku.interfaces.ModelInterface;

public abstract class UsuarioModel implements ModelInterface {

    private String nome;
    private String email;
    private TipoUsuario tipoUsuario;
    private Set<Integer> chavesContas;

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
