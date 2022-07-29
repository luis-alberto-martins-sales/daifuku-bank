package com.daifuku.usuario;


import com.daifuku.arquitetura.Service;
import com.daifuku.exceptions.UsuarioDuplicadoException;
import com.daifuku.utils.CNPJ;
import com.daifuku.utils.CPF;
import org.jetbrains.annotations.NotNull;

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
        verificarUsuarioDuplicado(usuarioModel);
        return super.DAO.cadastrarValor(usuarioModel);
    }

    private void verificarUsuarioDuplicado(UsuarioModel usuarioModel) {
        verificarUsuarioEmailDuplicado(usuarioModel);
        verificarUsuarioCpfCnpjDuplicado(usuarioModel);
    }

    private void verificarUsuarioCpfCnpjDuplicado(UsuarioModel usuarioModel) {
        boolean usuarioDuplicado=true;
        try {
            if (usuarioModel instanceof PessoaFisica){
                ((UsuarioDAO) super.DAO).encontrarUsuarioPorCpf(((PessoaFisica) usuarioModel).getCpf());
            } else {
                ((UsuarioDAO) super.DAO).encontrarUsuarioPorCnpj(((PessoaJuridica) usuarioModel).getCnpj());
            }
        } catch (NoSuchElementException e){
            usuarioDuplicado=false;
        }
        if(usuarioDuplicado){
            throw new UsuarioDuplicadoException();
        }
    }

    private void verificarUsuarioEmailDuplicado(UsuarioModel usuarioModel) {
        boolean usuarioDuplicado=true;
        try {
            ((UsuarioDAO) super.DAO).encontrarUsuarioPorEmail(usuarioModel.getEmail());
        } catch (NoSuchElementException e){
            usuarioDuplicado=false;
        }
        if(usuarioDuplicado){
            throw new UsuarioDuplicadoException();
        }
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



    protected void verificarCampoVazio(@NotNull UsuarioModel usuarioModel) {
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
