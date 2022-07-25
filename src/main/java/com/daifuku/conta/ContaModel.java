package main.java.com.daifuku.models;

import java.util.List;

import main.java.com.daifuku.enums.TipoConta;

public abstract class ContaModel {
    TipoConta tipoConta;
    List<OperacaoFinanceira> operacoesFinanceiras;
}
