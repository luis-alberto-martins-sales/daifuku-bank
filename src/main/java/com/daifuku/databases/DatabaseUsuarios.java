package com.daifuku.databases;

import com.daifuku.usuario.PessoaFisica;
import com.daifuku.usuario.PessoaJuridica;
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

        return clonarValor(mapa.values().stream().filter(u->u.getEmail().equals(email)).findAny().get());

    }

    public UsuarioModel encontrarUsuarioPorCpf(String cpf){
        return clonarValor(mapa.values().stream()
                .filter(u->u instanceof PessoaFisica)
                .filter(u->(((PessoaFisica) u).getCpf())
                        .equals(cpf)).findAny().get());

    }

    public UsuarioModel encontrarUsuarioPorCnpj(String cnpj){
        return clonarValor(mapa.values().stream()
                .filter(u->u instanceof PessoaJuridica)
                .filter(u->(((PessoaJuridica) u).getCnpj())
                        .equals(cnpj)).findAny().get());
    }
}
