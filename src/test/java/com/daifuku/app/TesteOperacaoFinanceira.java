package com.daifuku.app;

import com.daifuku.app.testUtils.TEST_CONSTANTS;
import com.daifuku.constants.RENDIMENTO_MENSAL;
import com.daifuku.constants.TAXA;
import com.daifuku.conta.ContaDAO;
import com.daifuku.conta.ContaService;
import com.daifuku.enums.TipoConta;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.exceptions.CampoVazioException;
import com.daifuku.exceptions.NegotialException;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraDAO;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraService;
import com.daifuku.usuario.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.daifuku.app.testUtils.GeradorParaTeste.*;
import static com.daifuku.app.testUtils.GeradorParaTeste.gerarMontante;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TesteOperacaoFinanceira {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private UsuarioService usuarioService = new UsuarioService(usuarioDAO);

    private ContaDAO contaDAO =new ContaDAO();
    private ContaService contaService = new ContaService(contaDAO,usuarioDAO);

    private OperacaoFinanceiraDAO operacaoFinanceiraDAO =new OperacaoFinanceiraDAO();
    private OperacaoFinanceiraService operacaoFinanceiraService = new OperacaoFinanceiraService(operacaoFinanceiraDAO,contaDAO,usuarioDAO);

    @Test
    public void deveVerificarSaldoInicialNulo(){
        Integer chaveConta = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);

        assertEquals(BigDecimal.ZERO.setScale(2),contaService.consultarSaldo(chaveConta));
    }

    //Depositar
    @Test
    public void deveDepositarMontanteContaCorrenteDePF() {
        Integer chaveConta = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);
        BigDecimal montante = gerarMontante();
        operacaoFinanceiraService.depositar(montante,chaveConta);

        assertEquals(montante,contaService.consultarSaldo(chaveConta));
    }

    @Test
    public void naoDeveDepositarMontanteVazio() {
        Integer chaveConta = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);
        assertThrows(CampoVazioException.class,()->operacaoFinanceiraService.depositar(null,chaveConta));

    }

    @Test
    public void naoDeveDepositarMontanteNegativo() {
        Integer chaveConta = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);
        assertThrows(IllegalArgumentException.class,()->operacaoFinanceiraService.depositar(new BigDecimal(-1),chaveConta));

    }

    //Sacar
    @Test
    public void deveSacarMontanteContaCorrenteDePF() {
        Integer chaveConta = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);
        BigDecimal montante = gerarMontante();
        operacaoFinanceiraService.depositar(montante,chaveConta);
        operacaoFinanceiraService.sacar(montante,chaveConta);

        assertEquals(BigDecimal.ZERO.setScale(2),contaService.consultarSaldo(chaveConta));
    }

    @Test
    public void naoDeveSacarValorSuperiorASaldo(){
        Integer chaveConta = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);
        BigDecimal montanteDepositado = gerarMontante();
        operacaoFinanceiraService.depositar(montanteDepositado,chaveConta);
        BigDecimal montanteSacado = gerarMontanteSuperiorA(montanteDepositado);

        assertThrows(NegotialException.class,()->operacaoFinanceiraService.sacar(montanteSacado,chaveConta));
    }

    @Test
    public void deveTaxarSaquePJ() {
        Integer chaveConta = gerarChaveContaUsuario(TipoUsuario.JURIDICA,TipoConta.CORRENTE,contaService,usuarioService);
        BigDecimal montante = gerarMontante();
        operacaoFinanceiraService.depositar(montante,chaveConta);
        operacaoFinanceiraService.sacar(montante.divide(BigDecimal.ONE.add(TAXA.SAQUE),2,RoundingMode.HALF_UP),chaveConta);

        assertEquals(BigDecimal.ZERO.setScale(2),contaService.consultarSaldo(chaveConta));
    }

    //Transferir
    @Test
    public void deveTransferirMontanteEntreDuasContas(){
        Integer chaveContaOrigem = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);
        BigDecimal montante = gerarMontante();
        operacaoFinanceiraService.depositar(montante,chaveContaOrigem);
        Integer chaveContaDestino = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);
        operacaoFinanceiraService.transferir(montante,chaveContaOrigem,chaveContaDestino);

        assertEquals(montante,contaService.consultarSaldo(chaveContaDestino));
        assertEquals(BigDecimal.ZERO.setScale(2),contaService.consultarSaldo(chaveContaOrigem));
    }

    @Test
    public void naoDeveTransferirMontanteSuperiorASaldo(){
        Integer chaveContaOrigem = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);
        BigDecimal montante = gerarMontante();
        operacaoFinanceiraService.depositar(montante,chaveContaOrigem);
        Integer chaveContaDestino = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);
        BigDecimal montanteSuperior = gerarMontanteSuperiorA(montante);

        assertThrows(NegotialException.class,()->operacaoFinanceiraService.transferir(montanteSuperior,chaveContaOrigem,chaveContaDestino));
    }

    @Test
    public void naoDeveTransferirParaContaInexistente(){
        Integer chaveContaOrigem = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);
        BigDecimal montante = gerarMontante();
        operacaoFinanceiraService.depositar(montante,chaveContaOrigem);

        assertThrows(NoSuchElementException.class,()->operacaoFinanceiraService.transferir(montante,chaveContaOrigem,-1));
    }

    @Test
    public void naoDeveTransferirDeContaInexistente(){
        Integer chaveContaDestino = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);
        BigDecimal montante = gerarMontante();

        assertThrows(NoSuchElementException.class,()->operacaoFinanceiraService.transferir(montante,-1,chaveContaDestino));
    }

    @Test
    public void deveTaxarTransferenciaPJ() {
        Integer chaveContaOrigem = gerarChaveContaUsuario(TipoUsuario.JURIDICA,TipoConta.CORRENTE,contaService,usuarioService);
        BigDecimal montante = gerarMontante();
        operacaoFinanceiraService.depositar(montante,chaveContaOrigem);
        Integer chaveContaDestino = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);
        operacaoFinanceiraService.transferir(montante.divide(BigDecimal.ONE.add(TAXA.TRANSF),2,RoundingMode.HALF_UP),chaveContaOrigem,chaveContaDestino);

        assertEquals(BigDecimal.ZERO.setScale(2),contaService.consultarSaldo(chaveContaOrigem));
    }

    //Investir
    @Test
    public void deveInvestirContaInvestimento(){
        Integer chaveConta = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.INVESTIMENTO,contaService,usuarioService);
        BigDecimal montante = gerarMontante();
        operacaoFinanceiraService.investir(montante,chaveConta);

        assertEquals(montante,contaService.consultarSaldo(chaveConta));
    }
    @Test
    public void naoDeveInvestirContaCorrente(){
        Integer chaveContaOrigem = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);
        BigDecimal montante = gerarMontante();

        assertThrows(NegotialException.class,()->operacaoFinanceiraService.investir(montante,chaveContaOrigem));
    }

    @Test
    public void deveRentabilizarPJSuperiorAPF(){
        Integer chaveContaPF = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.INVESTIMENTO,contaService,usuarioService);
        BigDecimal montante = gerarMontante();
        operacaoFinanceiraService.investir(montante,chaveContaPF);

        Integer chaveContaPJ = gerarChaveContaUsuario(TipoUsuario.JURIDICA,TipoConta.INVESTIMENTO,contaService,usuarioService);
        operacaoFinanceiraService.investir(montante,chaveContaPJ);

        BigDecimal rendimentoAnualPF = contaService.consultarRendimentoFuturo(chaveContaPF,LocalDateTime.now().plusYears(1l).plusDays(1));
        BigDecimal rendimentoAnualPJ = contaService.consultarRendimentoFuturo(chaveContaPJ,LocalDateTime.now().plusYears(1l).plusDays(1));
        BigDecimal rendimentoAnualPFAjustado = rendimentoAnualPF.multiply(BigDecimal.ONE.add(RENDIMENTO_MENSAL.PJ).pow(12).divide(BigDecimal.ONE.add(RENDIMENTO_MENSAL.PF).pow(12),RoundingMode.HALF_EVEN)).setScale(2,RoundingMode.HALF_UP);

        assert(rendimentoAnualPFAjustado.subtract(rendimentoAnualPJ).abs().compareTo(TEST_CONSTANTS.ERRO_ARREDONDAMENTO)<1);
    }

    //Consultar rendimento futuro
    @Test
    public void naoDeveConsultarRendimentoFuturoContaCorrente(){
        Integer chaveContaOrigem = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.CORRENTE,contaService,usuarioService);

        assertThrows(NegotialException.class,()->contaService.consultarRendimentoFuturo(chaveContaOrigem, LocalDateTime.MAX));
    }

    @Test
    public void naoDeveConsultarRendimentoFuturoContaSemSaldo(){
        Integer chaveContaOrigem = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.POUPANCA,contaService,usuarioService);

        assertThrows(NegotialException.class,()->contaService.consultarRendimentoFuturo(chaveContaOrigem, LocalDateTime.MAX));
    }

    @Test
    public void naoDeveConsultarRendimentoFuturoDataPassada(){
        Integer chaveContaOrigem = gerarChaveContaUsuario(TipoUsuario.FISICA,TipoConta.POUPANCA,contaService,usuarioService);
        BigDecimal montante = gerarMontante();
        operacaoFinanceiraService.depositar(montante,chaveContaOrigem);

        assertThrows(IllegalArgumentException.class,()->contaService.consultarRendimentoFuturo(chaveContaOrigem, LocalDateTime.MIN));
    }
}
