package com.daifuku.operacaoFinanceira;

import com.daifuku.arquitetura.Service;
import com.daifuku.constants.TAXA;
import com.daifuku.conta.ContaDAO;
import com.daifuku.enums.TipoConta;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.usuario.UsuarioDAO;

import java.math.BigDecimal;

public class OperacaoFinanceiraService extends Service<OperacaoFinanceiraModel> {

    private final UsuarioDAO usuarioDAO;

    private final ContaDAO contaDAO;

    public OperacaoFinanceiraService(OperacaoFinanceiraDAO operacaoFinanceiraDAO, ContaDAO contaDAO, UsuarioDAO usuarioDAO) {
        super(operacaoFinanceiraDAO);
        this.contaDAO=contaDAO;
        this.usuarioDAO=usuarioDAO;
    }

    public void depositar(BigDecimal montante,Integer chaveContaDestino) throws ExcecaoNegocial {
        OperacaoFinanceiraModel operacaoFinanceiraModel = new OperacaoFinanceiraModel(montante,null,chaveContaDestino);
        Integer chaveOperacaoFinanceira = this.cadastrarValor(operacaoFinanceiraModel);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceira,chaveContaDestino);
    }

    public void investir(BigDecimal montante,Integer chaveContaDestino) throws ExcecaoNegocial {
        if (contaDAO.recuperarValor(chaveContaDestino).getTipoConta()!= TipoConta.INVESTIMENTO){
            throw new ExcecaoNegocial("Somente é possível realizar investimento em conta-investimento.");
        }
        OperacaoFinanceiraModel operacaoFinanceiraModel = new OperacaoFinanceiraModel(montante,null,chaveContaDestino);
        Integer chaveOperacaoFinanceira = this.cadastrarValor(operacaoFinanceiraModel);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceira,chaveContaDestino);
    }

    public void sacar(BigDecimal montante,Integer chaveContaOrigem) throws ExcecaoNegocial {
        verificarSaldoOperacaoTaxada(montante, chaveContaOrigem, TAXA.SAQUE);
        OperacaoFinanceiraModel operacaoFinanceiraModel = new OperacaoFinanceiraModel(montante,chaveContaOrigem,null);
        Integer chaveOperacaoFinanceira = this.cadastrarValor(operacaoFinanceiraModel);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceira,chaveContaOrigem);
    }

    private void verificarSaldoOperacaoTaxada(BigDecimal montante, Integer chaveContaOrigem, BigDecimal taxa) throws ExcecaoNegocial {
        if (usuarioDAO.recuperarValor(contaDAO.recuperarValor(chaveContaOrigem).getChaveUsuario()).getTipoUsuario() != TipoUsuario.JURIDICA){
            verificarSaldo(montante,chaveContaOrigem);
            return;
        }
        BigDecimal montanteTaxa = montante.multiply(TAXA.SAQUE);
        verificarSaldo(montante.add(montanteTaxa),chaveContaOrigem);
        taxar(montanteTaxa,chaveContaOrigem);
    }

    private void taxar(BigDecimal montanteTaxa, Integer chaveContaOrigem) throws ExcecaoNegocial {
        OperacaoFinanceiraModel operacaoFinanceiraTaxa = new OperacaoFinanceiraModel(montanteTaxa,chaveContaOrigem,null);
        Integer chaveOperacaoFinanceiraTaxa = this.cadastrarValor(operacaoFinanceiraTaxa);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceiraTaxa,chaveContaOrigem);
    }


    public void transferir(BigDecimal montante,Integer chaveContaOrigem,Integer chaveContaDestino) throws ExcecaoNegocial {
        verificarSaldoOperacaoTaxada(montante, chaveContaOrigem,TAXA.TRANSF);
        OperacaoFinanceiraModel operacaoFinanceiraModel = new OperacaoFinanceiraModel(montante,chaveContaOrigem,chaveContaDestino);
        Integer chaveOperacaoFinanceira = this.cadastrarValor(operacaoFinanceiraModel);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceira,chaveContaOrigem);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceira,chaveContaDestino);

    }



    private void verificarSaldo(BigDecimal montante, Integer chaveConta) throws ExcecaoNegocial {
        if (montante.compareTo(contaDAO.consultarSaldo(chaveConta))==1){
            throw new ExcecaoNegocial("Saldo insuficiente.");
        }
    }


    @Override
    protected void verificarValorVazio(OperacaoFinanceiraModel valor){
        if (valor==null){
            throw new IllegalArgumentException();
        }
        if (valor.getMontante()==null){
            throw new IllegalArgumentException();
        }
        if (valor.getChaveContaOrigem()==null && valor.getChaveContaDestino()==null){
            throw new IllegalArgumentException();
        }

    }
    @Override
    protected void validarValor(OperacaoFinanceiraModel valor) throws ExcecaoNegocial {
        if (valor.getMontante().signum()!=1){
            throw new ExcecaoNegocial("Valor deve ser número positivo.");
        }
        if (valor.getChaveContaOrigem()!=null){
            contaDAO.recuperarValor(valor.getChaveContaOrigem());
        }
        if (valor.getChaveContaDestino()!=null){
            contaDAO.recuperarValor(valor.getChaveContaDestino());
        }
    }

}
