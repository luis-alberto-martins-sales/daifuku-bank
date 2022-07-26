package com.daifuku.usuario;

import java.util.List;
import java.util.Set;

import com.daifuku.conta.ContaModel;
import com.daifuku.enums.TipoUsuario;

public abstract class UsuarioModel {

    Integer chave;
    String nome;
    String email;
    TipoUsuario tipoUsuario;
    Set<ContaModel> contas;

    public UsuarioModel (String nome, String email){
       
        this.nome=nome;
        this.email=email;
    }

    public String getNome(){
        return this.nome;
    }
}
