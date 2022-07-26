package com.daifuku.operacaoFinanceira;

import com.daifuku.conta.ContaModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class OperacaoFinanceiraModel {
    BigDecimal montante;
    LocalDateTime data;
    ContaModel contaOrigem;
    ContaModel contaDestino;
}
