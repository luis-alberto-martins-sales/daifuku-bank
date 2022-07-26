package com.daifuku.usuario;


import com.daifuku.exceptions.ExcecaoNegocial;

import java.util.NoSuchElementException;

public class UsuarioService {
    UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO){
        this.usuarioDAO=usuarioDAO;
    }


    public UsuarioModel cadastrarUsuario(UsuarioModel usuarioModel) throws ExcecaoNegocial {
        if (usuarioModel == null) {
            throw new ExcecaoNegocial("Usuário vazio.");
        }
        if (usuarioModel.getNome()==null){
            throw new ExcecaoNegocial("Nome não informado.");
        }
        try {
            usuarioDAO.encontrarUsuarioPorNome(usuarioModel.getNome());
        } catch (NoSuchElementException e) {
            return usuarioDAO.adicionarUsuario(usuarioModel);
        } catch (Exception e) {
            throw e;
        }

        throw new ExcecaoNegocial("Usuário já cadastrado.");

        
    }
}
