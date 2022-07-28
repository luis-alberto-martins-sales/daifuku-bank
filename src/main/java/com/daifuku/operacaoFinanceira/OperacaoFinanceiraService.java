package com.daifuku.operacaoFinanceira;

import com.daifuku.arquitetura.Service;
import com.daifuku.constants.TAXA;
import com.daifuku.conta.ContaDAO;
import com.daifuku.enums.TipoConta;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.usuario.UsuarioDAO;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OperacaoFinanceiraService extends Service<OperacaoFinanceiraModel> {

    private final UsuarioDAO usuarioDAO;

    private final ContaDAO contaDAO;

    public OperacaoFinanceiraService(OperacaoFinanceiraDAO operacaoFinanceiraDAO, ContaDAO contaDAO, UsuarioDAO usuarioDAO) {
        super(operacaoFinanceiraDAO);
        this.contaDAO=contaDAO;
        this.usuarioDAO=usuarioDAO;
    }

    public void depositar(BigDecimal montante,Integer chaveContaDestino) {
        OperacaoFinanceiraModel operacaoFinanceiraModel = new OperacaoFinanceiraModel(montante,null,chaveContaDestino);
        Integer chaveOperacaoFinanceira = this.cadastrarValor(operacaoFinanceiraModel);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceira,chaveContaDestino);
    }

    public void investir(BigDecimal montante,Integer chaveContaDestino) {
        validarInvestimento(chaveContaDestino);
        OperacaoFinanceiraModel operacaoFinanceiraModel = new OperacaoFinanceiraModel(montante,null,chaveContaDestino);
        Integer chaveOperacaoFinanceira = this.cadastrarValor(operacaoFinanceiraModel);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceira,chaveContaDestino);
    }

    private void validarInvestimento(Integer chaveContaDestino) {
        if (contaDAO.recuperarValor(chaveContaDestino).getTipoConta()!= TipoConta.INVESTIMENTO){
            throw new ExcecaoNegocial("Somente é possível realizar investimento em conta-investimento.");
        }
    }

    public void sacar(BigDecimal montante,Integer chaveContaOrigem) {
        verificarSaldoOperacaoTaxada(montante, chaveContaOrigem, TAXA.SAQUE);
        OperacaoFinanceiraModel operacaoFinanceiraModel = new OperacaoFinanceiraModel(montante,chaveContaOrigem,null);
        Integer chaveOperacaoFinanceira = this.cadastrarValor(operacaoFinanceiraModel);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceira,chaveContaOrigem);
    }

    private void verificarSaldoOperacaoTaxada(BigDecimal montante, Integer chaveContaOrigem, BigDecimal taxa) {
        //FIXME
        if (usuarioDAO.recuperarValor(contaDAO.recuperarValor(chaveContaOrigem).getChaveUsuario()).getTipoUsuario() != TipoUsuario.JURIDICA){
            verificarSaldo(montante,chaveContaOrigem);
            return;
        }
        taxar(montante,chaveContaOrigem,taxa);
    }

    private void taxar(BigDecimal montante, Integer chaveContaOrigem, BigDecimal taxa) {
        BigDecimal montanteTaxa = montante.multiply(taxa).setScale(2, RoundingMode.HALF_UP);
        verificarSaldo(montante.add(montanteTaxa),chaveContaOrigem);
        OperacaoFinanceiraModel operacaoFinanceiraTaxa = new OperacaoFinanceiraModel(montanteTaxa,chaveContaOrigem,null);
        Integer chaveOperacaoFinanceiraTaxa = this.cadastrarValor(operacaoFinanceiraTaxa);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceiraTaxa,chaveContaOrigem);
    }


    public void transferir(BigDecimal montante,Integer chaveContaOrigem,Integer chaveContaDestino) {
        verificarSaldoOperacaoTaxada(montante, chaveContaOrigem,TAXA.TRANSF);
        OperacaoFinanceiraModel operacaoFinanceiraModel = new OperacaoFinanceiraModel(montante,chaveContaOrigem,chaveContaDestino);
        Integer chaveOperacaoFinanceira = this.cadastrarValor(operacaoFinanceiraModel);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceira,chaveContaOrigem);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceira,chaveContaDestino);

    }



    private void verificarSaldo(BigDecimal montante, Integer chaveConta) {
        if (montante.compareTo(contaDAO.consultarSaldo(chaveConta))==1){
            throw new ExcecaoNegocial("Saldo insuficiente.");
        }
    }


    @Override
    protected void verificarCampoVazio(OperacaoFinanceiraModel valor){
        if (valor.getMontante()==null){
            throw new IllegalArgumentException("Montante não informado.");
        }
        if (valor.getChaveContaOrigem()==null && valor.getChaveContaDestino()==null){
            throw new IllegalArgumentException("Chaves de contas não informadas.");
        }

    }
    @Override
    protected void validarValor(OperacaoFinanceiraModel valor) {
        if (valor.getMontante().signum()!=1) {
            throw new ExcecaoNegocial("Montante deve ser número positivo.");
        }
        if (valor.getMontante().scale()>2) {
            throw new ExcecaoNegocial("Montante deve ser número com no máximo 2 casas decimais.");
        }
        if (valor.getChaveContaOrigem()!=null) {
            contaDAO.recuperarValor(valor.getChaveContaOrigem());
        }
        if (valor.getChaveContaDestino()!=null) {
            contaDAO.recuperarValor(valor.getChaveContaDestino());
        }
    }

}
