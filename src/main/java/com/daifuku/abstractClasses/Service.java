package com.daifuku.abstractClasses;

import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.interfaces.DAOInterface;

public abstract class Service<T> {
    protected DAOInterface<T> DAO;

    protected Service(DAOInterface<T> DAO){
        this.DAO = DAO;
    }

    public Integer cadastrar(T valor) throws ExcecaoNegocial {
        verificarValorVazio(valor);
        validarValor(valor);
        return  DAO.criar(valor);
    }

    protected abstract void validarValor(T valor) throws ExcecaoNegocial;

    protected void verificarValorVazio(T valor) {
        if (valor==null){
            throw new IllegalArgumentException("Valor vazio.");
        }
    }

}
