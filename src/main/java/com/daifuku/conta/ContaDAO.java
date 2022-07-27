package com.daifuku.conta;

import com.daifuku.databases.DatabaseContas;
import com.daifuku.interfaces.DAOInterface;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraModel;

public class ContaDAO implements DAOInterface<ContaModel> {


    @Override
    public Integer criar(ContaModel valor) {
        return DatabaseContas.getInstancia().adicionarValor(valor);
    }

    @Override
    public ContaModel ler(Integer chave) {
        return DatabaseContas.getInstancia().encontrarValor(chave);
    }

    @Override
    public ContaModel atualizar(Integer chave, ContaModel valor) {
        return DatabaseContas.getInstancia().atualizarValor(chave,valor);
    }

    @Override
    public ContaModel deletar(Integer chave) {
        return null;
    }

    public void registrarOperacaoFinanceira(Integer chaveOperacaoFinanceira, OperacaoFinanceiraModel operacaoFinanceiraModel) {
        Integer chaveContaOrigem = operacaoFinanceiraModel.getChaveContaOrigem();
        if (chaveContaOrigem!=null){
            ContaModel contaModelOrigem = this.ler(chaveContaOrigem);
            contaModelOrigem.adicionarChaveOperacaoFinanceira(chaveOperacaoFinanceira);
            this.atualizar(chaveContaOrigem,contaModelOrigem);
        }

        Integer chaveContaDestino = operacaoFinanceiraModel.getChaveContaDestino();
        if (chaveContaDestino!=null){
            ContaModel contaModelDestino = this.ler(chaveContaDestino);
            contaModelDestino.adicionarChaveOperacaoFinanceira(chaveOperacaoFinanceira);
            this.atualizar(chaveContaDestino,contaModelDestino);
        }

    }
}
