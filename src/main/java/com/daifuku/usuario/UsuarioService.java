package com.daifuku.usuario;


import com.daifuku.abstractClasses.Service;
import com.daifuku.conta.ContaModel;
import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.utils.validaCpf;

import java.util.NoSuchElementException;
import java.util.Set;

public class UsuarioService extends Service<UsuarioModel> {

    UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO){
        super(usuarioDAO);
        this.usuarioDAO=usuarioDAO;
    }


    public Integer cadastrarUsuario(UsuarioModel usuarioModel) throws ExcecaoNegocial {
        verificarValorVazio(usuarioModel);
        validarValor(usuarioModel);
        try {
            usuarioDAO.encontrarUsuarioPorEmail(usuarioModel.getEmail());
        } catch (NoSuchElementException e) {
            return usuarioDAO.criar(usuarioModel);
        }

        throw new ExcecaoNegocial("Usuário já cadastrado.");

        
    }


    protected void validarValor(UsuarioModel usuarioModel) {
        if (!usuarioModel.getEmail().matches(".+@.+\\..+")){
            throw new IllegalArgumentException("Email inválido.");
        }
        if (usuarioModel instanceof PessoaFisica){
            PessoaFisica usuario = (PessoaFisica) usuarioModel;
            if(!validaCpf.testaCPF(usuario.getCpf())){
                throw new IllegalArgumentException("Cpf inválido.");
            }
        }

    }


    @Override
    protected void verificarValorVazio(UsuarioModel usuarioModel) {
        if (usuarioModel==null){
            throw new IllegalArgumentException();
        }
        if (usuarioModel.getNome()==null || usuarioModel.getNome().isEmpty()){
            throw new IllegalArgumentException();
        }
        if (usuarioModel.getEmail()==null || usuarioModel.getEmail().isEmpty()){
            throw new IllegalArgumentException();
        }
        if (usuarioModel instanceof PessoaFisica){
            PessoaFisica usuario = (PessoaFisica) usuarioModel;
            if (usuario.getCpf()==null || usuario.getCpf().isEmpty()){
                throw new IllegalArgumentException();
            }
        }
    }
}
