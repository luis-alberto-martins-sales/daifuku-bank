package com.daifuku.interfaces;


public interface DAOInterface<T> {

    Integer criar (T valor);

    T ler (Integer chave);

    T atualizar (Integer chave, T valor);

    T deletar(Integer chave);
}
