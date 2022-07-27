package com.daifuku.usuario;

import com.daifuku.enums.TipoUsuario;

public class PessoaJuridica extends UsuarioModel {

    private String cnpj;
    public PessoaJuridica(String nome, String email, String cnpj) {
        super(nome,email, TipoUsuario.JURIDICA);
        this.cnpj=cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }
}
