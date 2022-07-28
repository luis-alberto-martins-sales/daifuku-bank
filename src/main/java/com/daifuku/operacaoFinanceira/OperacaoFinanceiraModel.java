package com.daifuku.operacaoFinanceira;

import com.daifuku.arquitetura.ModelInterface;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperacaoFinanceiraModel implements ModelInterface {
    private final BigDecimal montante;
    private final LocalDateTime data;
    private final Integer chaveContaOrigem;
    private final Integer chaveContaDestino;

    OperacaoFinanceiraModel(BigDecimal montante, Integer chaveContaOrigem, Integer chaveContaDestino){
        this.montante=montante;
        this.data = LocalDateTime.now();
        this.chaveContaOrigem=chaveContaOrigem;
        this.chaveContaDestino=chaveContaDestino;
    }

    public BigDecimal getMontante() {
        return montante;
    }

    public Integer getChaveContaOrigem() {
        return chaveContaOrigem;
    }

    public Integer getChaveContaDestino() {
        return chaveContaDestino;
    }
}
