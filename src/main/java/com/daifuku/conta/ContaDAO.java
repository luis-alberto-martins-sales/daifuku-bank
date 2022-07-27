package com.daifuku.conta;

import com.daifuku.database.DatabaseContas;
import com.daifuku.database.DatabaseUsuarios;
import com.daifuku.interfaces.DAOInterface;

public class ContaDAO implements DAOInterface<ContaModel> {


    @Override
    public Integer criar(ContaModel valor) {
        return DatabaseContas.getInstancia().adicionarValor(valor);
    }

    @Override
    public ContaModel ler(Integer chave) {
        return null;
    }

    @Override
    public ContaModel atualizar(Integer chave, ContaModel valor) {
        return null;
    }

    @Override
    public ContaModel deletar(Integer chave) {
        return null;
    }
}
