package com.daifuku.conta;

import java.util.List;

import com.daifuku.enums.TipoConta;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraModel;

public abstract class ContaModel {
    TipoConta tipoConta;
    List<OperacaoFinanceiraModel> operacoesFinanceiras;
}
