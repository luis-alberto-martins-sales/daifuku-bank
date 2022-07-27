package com.daifuku.operacaoFinanceira;

import com.daifuku.interfaces.ModelInterface;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperacaoFinanceiraModel implements ModelInterface {
    private BigDecimal montante;
    private LocalDateTime data;
    private Integer chaveContaOrigem;
    private Integer chaveContaDestino;

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
