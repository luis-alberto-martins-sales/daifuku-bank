package com.daifuku.abstractClasses;

import com.daifuku.usuario.UsuarioModel;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.*;

public abstract class Database<T> {
    protected final TreeMap<Integer,T> mapa = new TreeMap<>();

    public Integer adicionarValor (T valor){
        if (!(valor instanceof Serializable)){
            throw new ClassCastException();
        }
        valor = (T) SerializationUtils.clone((Serializable) valor);
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

    public Set<T> recuperarValores() {
        Set<T> conjuntoValores = new HashSet<>();
        for (Integer chave:mapa.keySet()) {
            conjuntoValores.add(mapa.get(chave));
        }
        return Collections.unmodifiableSet(conjuntoValores) ;
    }

    public T encontrarValor (Integer chave){
        return mapa.get(chave);
    }

}
