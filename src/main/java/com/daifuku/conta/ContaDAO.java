package com.daifuku.conta;

import com.daifuku.databases.DatabaseContas;
import com.daifuku.databases.DatabaseOperacoesFinanceiras;
import com.daifuku.arquitetura.DAOInterface;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraModel;

import java.math.BigDecimal;
import java.util.List;

public class ContaDAO implements DAOInterface<ContaModel> {


    @Override
    public Integer cadastrarValor(ContaModel valor) {
        return DatabaseContas.getInstancia().cadastrarValor(valor);
    }

    @Override
    public ContaModel recuperarValor(Integer chave) {
        return DatabaseContas.getInstancia().recuperarValor(chave);
    }

    @Override
    public ContaModel atualizarValor(Integer chave, ContaModel valor) {
        return DatabaseContas.getInstancia().atualizarValor(chave,valor);
    }

    public void registrarOperacaoFinanceira(Integer chaveOperacaoFinanceira, Integer chaveConta) {
        ContaModel contaModel = this.recuperarValor(chaveConta);
        contaModel.adicionarChaveOperacaoFinanceira(chaveOperacaoFinanceira);
        this.atualizarValor(chaveConta,contaModel);
    }

    public BigDecimal consultarSaldo(Integer chaveConta) {
        BigDecimal saldo = BigDecimal.ZERO;
        List<Integer> chaves = recuperarValor(chaveConta).getChavesOperacoesFinanceiras();

        for (Integer chave: chaves) {
            OperacaoFinanceiraModel operacao = DatabaseOperacoesFinanceiras.getInstancia().recuperarValor(chave);
            if (chaveConta.equals(operacao.getChaveContaOrigem()) ){
                saldo=saldo.subtract(operacao.getMontante());
                continue;
            }
            saldo=saldo.add(operacao.getMontante());
        }

        return saldo;
    }
}
