package com.daifuku.databases;

import com.daifuku.abstractClasses.Database;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraModel;

public class DatabaseOperacoesFinanceiras extends Database<OperacaoFinanceiraModel> {

    private static final DatabaseOperacoesFinanceiras INSTANCIA = new DatabaseOperacoesFinanceiras();

    private DatabaseOperacoesFinanceiras() {
        if (INSTANCIA != null) {
            throw new IllegalStateException("Já instanciado.");
        }
    }

    public static DatabaseOperacoesFinanceiras getInstancia() {
        return INSTANCIA;
    }

}
