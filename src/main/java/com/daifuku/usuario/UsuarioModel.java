package com.daifuku.usuario;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.daifuku.conta.ContaModel;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.interfaces.ModelInterface;

public abstract class UsuarioModel implements ModelInterface {

    String nome;
    String email;
    TipoUsuario tipoUsuario;
    Set<Integer> chavesContas;

    public UsuarioModel (String nome, String email){
       
        this.nome=nome;
        this.email=email;
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
