package com.daifuku.operacaoFinanceira;

import com.daifuku.databases.DatabaseOperacoesFinanceiras;
import com.daifuku.interfaces.DAOInterface;

public class OperacaoFinanceiraDAO implements DAOInterface<OperacaoFinanceiraModel> {

    @Override
    public Integer criar(OperacaoFinanceiraModel valor) {
        return DatabaseOperacoesFinanceiras.getInstancia().adicionarValor(valor);
    }

    @Override
    public OperacaoFinanceiraModel ler(Integer chave) {
        return DatabaseOperacoesFinanceiras.getInstancia().encontrarValor(chave);
    }

    @Override
    public OperacaoFinanceiraModel atualizar(Integer chave, OperacaoFinanceiraModel valor) {
        return null;
    }

    @Override
    public OperacaoFinanceiraModel deletar(Integer chave) {
        return null;
    }
}
