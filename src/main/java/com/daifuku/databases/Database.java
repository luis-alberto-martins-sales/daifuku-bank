package com.daifuku.databases;

import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.*;

public abstract class Database<T> {
     final TreeMap<Integer,T> mapa = new TreeMap<>();

    public Integer cadastrarValor(T valor){
        if (!(valor instanceof Serializable)){
            throw new ClassCastException();
        }
        valor = clonarValor(valor);
        Integer ultimaChave;
        try {
            ultimaChave= mapa.lastKey();
        } catch (NoSuchElementException e) {
            mapa.put(0,valor);
            return 0;
        }
        mapa.put(ultimaChave+1,valor);
        return ultimaChave+1;
    }

    public Set<T> recuperarTodosValores() {
        Set<T> conjuntoValores = new HashSet<>();
        for (Integer chave:mapa.keySet()) {
            conjuntoValores.add(mapa.get(chave));
        }
        return Collections.unmodifiableSet(conjuntoValores) ;
    }

    public T recuperarValor(Integer chave){
        return clonarValor(mapa.get(chave));
    }

    public T atualizarValor (Integer chave, T novoValor){
        return clonarValor(mapa.put(chave,novoValor));
    }

    protected T clonarValor(T valor){
        if (valor==null){
            throw new NoSuchElementException("Valor inválido.");
        }
        return (T) SerializationUtils.clone((Serializable) valor);
    }
}
