package com.daifuku.usuario;

public class PessoaFisica extends UsuarioModel {

    private String cpf;
    public PessoaFisica(String nome, String email,String cpf) {
        super(nome,email);
        super.tipoUsuario=tipoUsuario.FISICA;
        this.cpf=cpf;
    }
}
