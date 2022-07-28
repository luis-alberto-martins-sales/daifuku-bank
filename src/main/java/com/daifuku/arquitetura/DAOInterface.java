package com.daifuku.arquitetura;


public interface DAOInterface<T> {

    Integer cadastrarValor(T valor);

    T recuperarValor(Integer chave);

    T atualizarValor(Integer chave, T valor);

    //T deletarValor (Integer chave, T valor);
}
