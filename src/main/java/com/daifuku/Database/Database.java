package com.daifuku.database;

import java.util.*;

import com.daifuku.conta.ContaModel;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraModel;
import com.daifuku.usuario.UsuarioModel;
import org.apache.commons.lang.SerializationUtils;

public enum Database {
    INSTANCIA;

    private final TreeMap<Integer, UsuarioModel> usuarios = new TreeMap<>();
    
    private Map<Integer, ContaModel> contas;

    private Map<Integer, OperacaoFinanceiraModel> operacoesFinanceiras;

    public UsuarioModel adicionarUsuario (UsuarioModel usuarioModel){

        usuarioModel = (UsuarioModel) SerializationUtils.clone(usuarioModel);
        Integer ultimaChave;
        try {
            ultimaChave=usuarios.lastKey();
        } catch (NoSuchElementException e) {
            usuarioModel.setChave(0);
            usuarios.put(0,usuarioModel);
            return usuarioModel;
        }
        usuarioModel.setChave(ultimaChave+1);
        usuarios.put(ultimaChave+1,usuarioModel);
        return usuarioModel;
    }

    public UsuarioModel encontrarUsuarioPorEmail(String email){
        for (Integer chave:usuarios.keySet()) {
            if (usuarios.get(chave).getEmail()==email) {
                return usuarios.get(chave);
            }
        }

        throw new NoSuchElementException();
    }

    public Set<UsuarioModel> recuperarUsuarios() {
        Set<UsuarioModel> conjuntoUsuarios = new HashSet<>();
        for (Integer chave:usuarios.keySet()) {
            conjuntoUsuarios.add(usuarios.get(chave));
        }
        return Collections.unmodifiableSet(conjuntoUsuarios) ;
    }
}
