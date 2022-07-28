package com.daifuku.usuario;

import com.daifuku.enums.TipoUsuario;

public class PessoaFisica extends UsuarioModel {

    private final String cpf;
    public PessoaFisica(String nome, String email,String cpf) {
        super(nome,email, TipoUsuario.FISICA);
        this.cpf=cpf;
    }

    public String getCpf() {
        return cpf;
    }
}
