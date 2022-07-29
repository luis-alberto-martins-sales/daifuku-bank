package com.daifuku.app;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.daifuku.conta.ContaDAO;
import com.daifuku.conta.ContaModel;
import com.daifuku.conta.ContaService;
import com.daifuku.exceptions.NegotialException;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraDAO;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraService;
import com.daifuku.usuario.*;
import  com.daifuku.enums.TipoConta;
import com.daifuku.utils.CNPJ;
import com.daifuku.utils.CPF;

public class App 
{
    public static void main( String[] args ) throws NegotialException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ContaDAO  contaDAO = new ContaDAO();
        OperacaoFinanceiraDAO operacaoFinanceiraDAO = new OperacaoFinanceiraDAO();

        UsuarioService usuarioService = new UsuarioService(usuarioDAO);
        ContaService contaService = new ContaService(contaDAO, usuarioDAO);
        OperacaoFinanceiraService operacaoFinanceiraService = new OperacaoFinanceiraService(operacaoFinanceiraDAO,contaDAO,usuarioDAO);

        //Cadastrar Usuário pessoa física
        UsuarioModel usuarioPF = new PessoaFisica("usuarioPF","usuarioPF@provedor.com", CPF.gerarCPF());
        Integer chaveUsuarioPF = usuarioService.cadastrarValor(usuarioPF);

        //Cadastrar conta poupança
        ContaModel contaPoupanca = new ContaModel(chaveUsuarioPF, TipoConta.POUPANCA);
        Integer chaveContaPoupanca = contaService.cadastrarValor(contaPoupanca);

        //Depositar
        operacaoFinanceiraService.depositar(new BigDecimal(7), chaveContaPoupanca);

        //Investir
        operacaoFinanceiraService.depositar(new BigDecimal(3), chaveContaPoupanca);

        //Consultar saldo
        contaService.consultarSaldo(chaveContaPoupanca);

        //Sacar
        operacaoFinanceiraService.sacar(new BigDecimal(2), chaveContaPoupanca);

        //Criar usuário pessoa jurídica, criar conta-investimento, investir
        UsuarioModel usuarioPJ = new PessoaJuridica("usuarioPJ","usuarioPJ@provedor.com", CNPJ.gerarCNPJ());
        Integer chaveUsuarioPJ = usuarioService.cadastrarValor(usuarioPJ);
        ContaModel contaInvestimento = new ContaModel(chaveUsuarioPJ, TipoConta.INVESTIMENTO);
        Integer chaveContaInvestimento = contaService.cadastrarValor(contaInvestimento);
        operacaoFinanceiraService.investir(new BigDecimal(6), chaveContaInvestimento);

        //Tranferir de conta-investimento de pessoa jurídica para conta-poupança de pessoa física
        operacaoFinanceiraService.transferir(new BigDecimal(4),chaveContaInvestimento,chaveContaPoupanca);

        //Consultar rendimento de conta após um ano
        contaService.consultarRendimentoFuturo(chaveContaInvestimento, LocalDateTime.now().plusYears(1L).plusDays(1L));

        //Para mais casos de uso, ver classes de teste
        //Cobertura de testes ~85% (jacoco)
    }
}
