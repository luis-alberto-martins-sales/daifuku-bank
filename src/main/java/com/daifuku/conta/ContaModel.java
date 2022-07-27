package com.daifuku.conta;

import java.math.BigDecimal;
import java.util.List;

import com.daifuku.enums.TipoConta;
import com.daifuku.interfaces.ModelInterface;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraModel;

public class ContaModel implements ModelInterface {
    Integer chaveUsuario;
    TipoConta tipoConta;
    List<OperacaoFinanceiraModel> operacoesFinanceiras;
    BigDecimal saldo = BigDecimal.ZERO;

    public ContaModel(Integer chave, TipoConta tipoConta) {
        this.chaveUsuario=chave;
        this.tipoConta=tipoConta;
    }

    public Integer getChaveUsuario() {
        return chaveUsuario;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }
}
