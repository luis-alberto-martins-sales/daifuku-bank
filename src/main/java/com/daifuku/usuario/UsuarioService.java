package com.daifuku.usuario;


import com.daifuku.arquitetura.Service;
import com.daifuku.exceptions.CampoVazioException;
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
    public Integer cadastrarValor(UsuarioModel usuario) {
        verificarValorVazio(usuario);
        verificarCampoVazio(usuario);
        validarValor(usuario);
        verificarUsuarioDuplicado(usuario);
        return super.DAO.cadastrarValor(usuario);
    }

    private void verificarUsuarioDuplicado(UsuarioModel usuario) {
        verificarUsuarioEmailDuplicado(usuario);
        verificarUsuarioCpfCnpjDuplicado(usuario);
    }

    private void verificarUsuarioCpfCnpjDuplicado(UsuarioModel usuario) {
        boolean usuarioDuplicado=true;
        try {
            if (usuario instanceof PessoaFisica){
                ((UsuarioDAO) super.DAO).encontrarUsuarioPorCpf(((PessoaFisica) usuario).getCpf());
            } else {
                ((UsuarioDAO) super.DAO).encontrarUsuarioPorCnpj(((PessoaJuridica) usuario).getCnpj());
            }
        } catch (NoSuchElementException e){
            usuarioDuplicado=false;
        }
        if(usuarioDuplicado){
            throw new UsuarioDuplicadoException();
        }
    }

    private void verificarUsuarioEmailDuplicado(UsuarioModel usuario) {
        boolean usuarioDuplicado=true;
        try {
            ((UsuarioDAO) super.DAO).encontrarUsuarioPorEmail(usuario.getEmail());
        } catch (NoSuchElementException e){
            usuarioDuplicado=false;
        }
        if(usuarioDuplicado){
            throw new UsuarioDuplicadoException();
        }
    }


    protected void validarValor(UsuarioModel usuario) {
        if (!usuario.getEmail().matches(".+@.+\\..+")){
            throw new IllegalArgumentException("Email inválido.");
        }
        if (usuario instanceof PessoaFisica){
            PessoaFisica usuarioPF = (PessoaFisica) usuario;
            if(!CPF.testarCPF(usuarioPF.getCpf())){
                throw new IllegalArgumentException("Cpf inválido.");
            }
        }
        if (usuario instanceof PessoaJuridica){
            PessoaJuridica usuarioPJ = (PessoaJuridica) usuario;
            if(!CNPJ.testarCNPJ(usuarioPJ.getCnpj())){
                throw new IllegalArgumentException("Cnpj inválido.");
            }
        }

    }

    protected void verificarCampoVazio(@NotNull UsuarioModel usuario) {
        if (usuario.getNome()==null || usuario.getNome().isEmpty()){
            throw new CampoVazioException("nome");
        }
        if (usuario.getEmail()==null || usuario.getEmail().isEmpty()){
            throw new CampoVazioException("e-mail");
        }
        if (usuario instanceof PessoaFisica){
            PessoaFisica usuarioPF = (PessoaFisica) usuario;
            if (usuarioPF.getCpf()==null || usuarioPF.getCpf().isEmpty()){
                throw new CampoVazioException("CPF");
            }
        }
        if (usuario instanceof PessoaJuridica){
            PessoaJuridica usuarioPJ = (PessoaJuridica) usuario;
            if (usuarioPJ.getCnpj()==null || usuarioPJ.getCnpj().isEmpty()){
                throw new CampoVazioException("CNPJ");
            }
        }
    }
}
