package com.daifuku.arquitetura;

import com.daifuku.exceptions.NegotialException;

public abstract class Service<T> {
    protected final DAOInterface<T> DAO;

    protected Service(DAOInterface<T> DAO){
        this.DAO = DAO;
    }

    public Integer cadastrarValor(T valor) throws NegotialException {
        verificarValorVazio(valor);
        verificarCampoVazio(valor);
        validarValor(valor);
        return DAO.cadastrarValor(valor);
    }

    public T recuperarValor(Integer chave){
        return DAO.recuperarValor(chave);
    }

    protected abstract void validarValor(T valor) throws NegotialException;

    protected void verificarValorVazio(T valor) {
        if (valor==null){
            throw new IllegalArgumentException("Valor vazio.");
        }
    }

    protected void verificarCampoVazio(T valor) {
        if (valor==null){
            throw new IllegalArgumentException("Valor vazio.");
        }
    }

}
