package com.daifuku.usuario;


import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.utils.validaCpf;

import java.util.NoSuchElementException;
import java.util.Set;

public class UsuarioService {
    UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO){
        this.usuarioDAO=usuarioDAO;
    }


    public UsuarioModel cadastrarUsuario(UsuarioModel usuarioModel) throws ExcecaoNegocial {
        verificarDadoVazio(usuarioModel);
        validarDadosUsuario(usuarioModel);
        try {
            usuarioDAO.encontrarUsuarioPorEmail(usuarioModel.getEmail());
        } catch (NoSuchElementException e) {
            return usuarioDAO.criar(usuarioModel);
        } catch (Exception e) {
            throw e;
        }

        throw new ExcecaoNegocial("Usuário já cadastrado.");

        
    }

    public Set<UsuarioModel> recuperarUsuarios (){
        return usuarioDAO.recuperarUsuarios();
    }

    private void validarDadosUsuario(UsuarioModel usuarioModel) {
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

    private void verificarDadoVazio(UsuarioModel usuarioModel) {
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
