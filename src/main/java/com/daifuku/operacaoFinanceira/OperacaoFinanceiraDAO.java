package com.daifuku.operacaoFinanceira;

import com.daifuku.databases.DatabaseOperacoesFinanceiras;
import com.daifuku.arquitetura.DAOInterface;

public class OperacaoFinanceiraDAO implements DAOInterface<OperacaoFinanceiraModel> {

    @Override
    public Integer cadastrarValor(OperacaoFinanceiraModel valor) {
        return DatabaseOperacoesFinanceiras.getInstancia().cadastrarValor(valor);
    }

    @Override
    public OperacaoFinanceiraModel recuperarValor(Integer chave) {
        return DatabaseOperacoesFinanceiras.getInstancia().recuperarValor(chave);
    }

    @Override
    public OperacaoFinanceiraModel atualizarValor(Integer chave, OperacaoFinanceiraModel valor) {
        return DatabaseOperacoesFinanceiras.getInstancia().atualizarValor(chave, valor);
    }

}
