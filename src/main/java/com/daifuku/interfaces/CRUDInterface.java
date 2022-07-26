package com.daifuku.interfaces;

public interface CRUDInterface<T> {
    T criar (T valor);

    T ler (int chave);

    T atualizar (int chave, T valor);

    T deletar(int chave);
}
