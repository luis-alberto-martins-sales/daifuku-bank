package com.daifuku.interfaces;

public interface CRUDInterface<T> {
    Integer criar (T valor);

    T ler (Integer chave);

    T atualizar (Integer chave, T valor);

    T deletar(Integer chave);
}
