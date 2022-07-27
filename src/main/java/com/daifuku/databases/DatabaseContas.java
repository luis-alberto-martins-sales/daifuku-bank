package com.daifuku.databases;

import com.daifuku.abstractClasses.Database;
import com.daifuku.conta.ContaModel;

public class DatabaseContas extends Database<ContaModel> {

    private static final DatabaseContas INSTANCIA = new DatabaseContas();

    private DatabaseContas() {
        if (INSTANCIA != null) {
            throw new IllegalStateException("Já instanciado.");
        }
    }

    public static DatabaseContas getInstancia() {
        return INSTANCIA;
    }

}
