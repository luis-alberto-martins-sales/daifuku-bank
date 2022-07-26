package com.daifuku.conta;

import java.math.BigDecimal;
import java.util.List;

import com.daifuku.enums.TipoConta;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraModel;

public abstract class ContaModel {
    Integer chave;
    TipoConta tipoConta;
    List<OperacaoFinanceiraModel> operacoesFinanceiras;
    BigDecimal saldo = BigDecimal.ZERO;
}
