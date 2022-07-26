package com.daifuku.database;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import com.daifuku.conta.ContaModel;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraModel;
import com.daifuku.usuario.UsuarioModel;

public enum Database {
    INSTANCIA;

    private final TreeMap<Integer, UsuarioModel> usuarios = new TreeMap<>();
    
    private Map<Integer, ContaModel> contas;

    private Map<Integer, OperacaoFinanceiraModel> operacoesFinanceiras;

    public UsuarioModel adicionarUsuario (UsuarioModel usuarioModel){
        Integer ultimaChave;
        try {
            ultimaChave=usuarios.lastKey();
        } catch (NoSuchElementException e) {
            usuarios.put(0,usuarioModel);
            return usuarioModel;
        }
        usuarios.put(ultimaChave+1,usuarioModel);
        return usuarioModel;
    }

    public UsuarioModel encontrarUsuario(String nome){
        for (Integer chave:usuarios.keySet()) {
            if (usuarios.get(chave).getNome().equals(nome)) {
                return usuarios.get(chave);
            }
        }

        throw new NoSuchElementException();
    }
}
