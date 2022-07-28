package com.daifuku.usuario;


import com.daifuku.arquitetura.Service;
import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.utils.CNPJ;
import com.daifuku.utils.CPF;

import java.util.NoSuchElementException;

public class UsuarioService extends Service<UsuarioModel> {


    public UsuarioService(UsuarioDAO usuarioDAO){
        super(usuarioDAO);
    }

    @Override
    public Integer cadastrarValor(UsuarioModel usuarioModel) {
        verificarValorVazio(usuarioModel);
        verificarCampoVazio(usuarioModel);
        validarValor(usuarioModel);

        try {
            ((UsuarioDAO) super.DAO).encontrarUsuarioPorEmail(usuarioModel.getEmail());
        } catch (NoSuchElementException e) {
            return super.DAO.cadastrarValor(usuarioModel);
        }

        throw new ExcecaoNegocial("Usuário já cadastrado.");

    }


    protected void validarValor(UsuarioModel usuarioModel) {
        if (!usuarioModel.getEmail().matches(".+@.+\\..+")){
            throw new IllegalArgumentException("Email inválido.");
        }
        if (usuarioModel instanceof PessoaFisica){
            PessoaFisica usuario = (PessoaFisica) usuarioModel;
            if(!CPF.testarCPF(usuario.getCpf())){
                throw new IllegalArgumentException("Cpf inválido.");
            }
        }
        if (usuarioModel instanceof PessoaJuridica){
            PessoaJuridica usuario = (PessoaJuridica) usuarioModel;
            if(!CNPJ.testarCNPJ(usuario.getCnpj())){
                throw new IllegalArgumentException("Cnpj inválido.");
            }
        }

    }



    protected void verificarCampoVazio(UsuarioModel usuarioModel) {
        if (usuarioModel.getNome()==null || usuarioModel.getNome().isEmpty()){
            throw new IllegalArgumentException("Nome não informado.");
        }
        if (usuarioModel.getEmail()==null || usuarioModel.getEmail().isEmpty()){
            throw new IllegalArgumentException("E-mail não informado.");
        }
        if (usuarioModel instanceof PessoaFisica){
            PessoaFisica usuario = (PessoaFisica) usuarioModel;
            if (usuario.getCpf()==null || usuario.getCpf().isEmpty()){
                throw new IllegalArgumentException("CPF não informado.");
            }
        }
        if (usuarioModel instanceof PessoaJuridica){
            PessoaJuridica usuario = (PessoaJuridica) usuarioModel;
            if (usuario.getCnpj()==null || usuario.getCnpj().isEmpty()){
                throw new IllegalArgumentException("CNPJ não informado.");
            }
        }
    }
}
