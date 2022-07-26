package com.daifuku.conta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.daifuku.enums.TipoConta;
import com.daifuku.arquitetura.ModelInterface;

public class ContaModel implements ModelInterface {
    private final Integer chaveUsuario;
    private final TipoConta tipoConta;
    private final List<Integer> chavesOperacoesFinanceiras = new ArrayList<>();

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

    public void adicionarChaveOperacaoFinanceira (Integer chave){
        this.chavesOperacoesFinanceiras.add(chave);
    }

    public List<Integer> getChavesOperacoesFinanceiras() {
        return Collections.unmodifiableList(chavesOperacoesFinanceiras);
    }
}
