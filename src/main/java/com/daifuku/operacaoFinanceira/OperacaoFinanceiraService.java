package com.daifuku.operacaoFinanceira;

import com.daifuku.abstractClasses.Service;
import com.daifuku.constants.TAXA;
import com.daifuku.conta.ContaDAO;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.usuario.UsuarioDAO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class OperacaoFinanceiraService extends Service<OperacaoFinanceiraModel> {

    UsuarioDAO usuarioDAO;

    ContaDAO contaDAO;

    public OperacaoFinanceiraService(OperacaoFinanceiraDAO operacaoFinanceiraDAO, ContaDAO contaDAO,UsuarioDAO usuarioDAO) {
        super(operacaoFinanceiraDAO);
        this.contaDAO=contaDAO;
        this.usuarioDAO=usuarioDAO;
    }

    public void depositar(BigDecimal montante,Integer chaveContaDestino) throws ExcecaoNegocial {
        OperacaoFinanceiraModel operacaoFinanceiraModel = new OperacaoFinanceiraModel(montante,null,chaveContaDestino);
        Integer chaveOperacaoFinanceira = this.cadastrar(operacaoFinanceiraModel);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceira,operacaoFinanceiraModel);
    }

    public void sacar(BigDecimal montante,Integer chaveContaOrigem) throws ExcecaoNegocial {
        if (usuarioDAO.ler(contaDAO.ler(chaveContaOrigem).getChaveUsuario()).getTipoUsuario() == TipoUsuario.JURIDICA){
            montante=montante.multiply(BigDecimal.ONE.add(TAXA.SAQUE));
        }
        verificarSaldo(montante, chaveContaOrigem);
        OperacaoFinanceiraModel operacaoFinanceiraModel = new OperacaoFinanceiraModel(montante,chaveContaOrigem,null);
        Integer chaveOperacaoFinanceira = this.cadastrar(operacaoFinanceiraModel);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceira,operacaoFinanceiraModel);
    }

    public void transferir(BigDecimal montante,Integer chaveContaOrigem,Integer chaveContaDestino) throws ExcecaoNegocial {
        BigDecimal taxa = BigDecimal.ZERO;
        if (usuarioDAO.ler(contaDAO.ler(chaveContaOrigem).getChaveUsuario()).getTipoUsuario() == TipoUsuario.JURIDICA){
            taxa=montante.multiply(TAXA.TRANSF);
        }
        verificarSaldo(montante.add(taxa), chaveContaOrigem);
        OperacaoFinanceiraModel operacaoFinanceiraModel = new OperacaoFinanceiraModel(montante,chaveContaOrigem,chaveContaDestino);
        Integer chaveOperacaoFinanceira = this.cadastrar(operacaoFinanceiraModel);
        contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceira,operacaoFinanceiraModel);
        if (!taxa.equals(BigDecimal.ZERO)){
            OperacaoFinanceiraModel operacaoFinanceiraTaxa = new OperacaoFinanceiraModel(taxa,chaveContaOrigem,null);
            Integer chaveOperacaoFinanceiraTaxa = this.cadastrar(operacaoFinanceiraTaxa);
            contaDAO.registrarOperacaoFinanceira(chaveOperacaoFinanceiraTaxa,operacaoFinanceiraTaxa);
        }
    }

    private void verificarSaldo(BigDecimal montante, Integer chaveConta) throws ExcecaoNegocial {
        if (montante.compareTo(consultarSaldo(chaveConta))==1){
            throw new ExcecaoNegocial("Saldo insuficiente");
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
        try {
            contaDAO.ler(valor.getChaveContaOrigem());
            contaDAO.ler(valor.getChaveContaDestino());
        } catch (NullPointerException e) {
            ;
        } catch (NoSuchElementException e) {
            throw new ExcecaoNegocial("Conta não encontrada.");
        }
    }

    public BigDecimal consultarSaldo(Integer chaveConta) {
        BigDecimal saldo = BigDecimal.ZERO;
        List<Integer> chaves = contaDAO.ler(chaveConta).getChavesOperacoesFinanceiras();
        for (Integer chave: chaves) {
            OperacaoFinanceiraModel operacao = DAO.ler(chave);
            if (chaveConta.equals(operacao.getChaveContaOrigem()) ){
                saldo=saldo.subtract(operacao.getMontante());
            } else {
                saldo=saldo.add(operacao.getMontante());
            }
        }
        return saldo;
    }

}
