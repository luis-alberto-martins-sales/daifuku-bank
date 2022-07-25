package main.java.com.daifuku.models;

import java.math.BigDecimal;
import java.util.Date;

public class OperacaoFinanceira {
    BigDecimal montante;
    Date data;
    Conta contaOrigem;
    Conta contaDestino;
}
