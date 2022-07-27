package com.daifuku.conta;

import com.daifuku.abstractClasses.Service;
import com.daifuku.enums.TipoConta;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraDAO;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraModel;
import com.daifuku.usuario.UsuarioDAO;
import com.daifuku.usuario.UsuarioModel;

import java.util.NoSuchElementException;

public class ContaService extends Service<ContaModel> {

    UsuarioDAO usuarioDAO;

    public ContaService(ContaDAO contaDAO, UsuarioDAO usuarioDAO) {
        super(contaDAO);
        this.usuarioDAO=usuarioDAO;
    }

    protected void validarValor(ContaModel contaModel) throws ExcecaoNegocial {
        UsuarioModel usuario;
        try {
            usuario=usuarioDAO.ler(contaModel.getChaveUsuario());
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        if (usuario.getTipoUsuario()== TipoUsuario.JURIDICA
                && contaModel.getTipoConta()== TipoConta.POUPANCA){
            throw new ExcecaoNegocial("Pessoa jurídica não pode abrir conta poupança.");
        }
    }
    @Override
    protected void verificarValorVazio(ContaModel contaModel) {
        if (contaModel==null){
            throw new IllegalArgumentException();
        }
        if (contaModel.getChaveUsuario()==null){
            throw new IllegalArgumentException();
        }
        if (contaModel.getTipoConta()==null){
            throw new IllegalArgumentException();
        }
    }

}
