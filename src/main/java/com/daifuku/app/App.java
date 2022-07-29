package com.daifuku.app;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.daifuku.conta.ContaDAO;
import com.daifuku.conta.ContaModel;
import com.daifuku.conta.ContaService;
import com.daifuku.exceptions.NegotialException;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraDAO;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraService;
import com.daifuku.usuario.PessoaFisica;
import com.daifuku.usuario.UsuarioDAO;
import com.daifuku.usuario.UsuarioModel;
import com.daifuku.usuario.UsuarioService;
import  com.daifuku.enums.TipoConta;

public class App 
{
    public static void main( String[] args ) throws NegotialException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ContaDAO  contaDAO = new ContaDAO();
        OperacaoFinanceiraDAO operacaoFinanceiraDAO = new OperacaoFinanceiraDAO();

        UsuarioService usuarioService = new UsuarioService(usuarioDAO);
        ContaService contaService = new ContaService(contaDAO, usuarioDAO);
        OperacaoFinanceiraService operacaoFinanceiraService = new OperacaoFinanceiraService(operacaoFinanceiraDAO,contaDAO,usuarioDAO);

        UsuarioModel usuarioPF = new PessoaFisica("nome","email@email.com","45771089095");
        Integer chaveUsuario = usuarioService.cadastrarValor(usuarioPF);

        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.POUPANCA);

        Integer chaveConta = contaService.cadastrarValor(contaCorrente);

        operacaoFinanceiraService.depositar(new BigDecimal(7), chaveConta);

        operacaoFinanceiraService.depositar(new BigDecimal(3), chaveConta);

        operacaoFinanceiraService.sacar(new BigDecimal(2), chaveConta);

        System.out.println(contaService.consultarRendimentoFuturo(chaveConta, LocalDateTime.now().plusYears(1L).plusDays(1L)));

    }
}
