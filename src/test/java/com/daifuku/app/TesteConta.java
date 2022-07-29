package com.daifuku.app;

import com.daifuku.conta.ContaDAO;
import com.daifuku.conta.ContaModel;
import com.daifuku.conta.ContaService;
import com.daifuku.enums.TipoConta;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.exceptions.CampoVazioException;
import com.daifuku.exceptions.NegotialException;
import com.daifuku.usuario.*;
import org.junit.Test;

import java.util.NoSuchElementException;

import static com.daifuku.app.testUtils.GeradorParaTeste.gerarChaveUsuarioValido;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TesteConta {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private UsuarioService usuarioService = new UsuarioService(usuarioDAO);

    private ContaDAO contaDAO =new ContaDAO();
    private ContaService contaService = new ContaService(contaDAO,usuarioDAO);


    @Test
    public void deveCadastrarContaCorrenteDePF() {
        Integer chaveUsuario = gerarChaveUsuarioValido(TipoUsuario.FISICA,usuarioService);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.CORRENTE);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);

        assert(contaRecuperada.getTipoConta()==TipoConta.CORRENTE);
        assert(contaRecuperada.getChaveUsuario().equals(chaveUsuario));
    }

    @Test
    public void deveCadastrarContaPoupancaDePF() {
        Integer chaveUsuario = gerarChaveUsuarioValido(TipoUsuario.FISICA,usuarioService);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.POUPANCA);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);

        assert(contaRecuperada.getChaveUsuario().equals(chaveUsuario));
        assert(contaRecuperada.getTipoConta()==TipoConta.POUPANCA);
    }

    @Test
    public void deveCadastrarContaInvestimentoDePF() {
        Integer chaveUsuario = gerarChaveUsuarioValido(TipoUsuario.FISICA,usuarioService);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.INVESTIMENTO);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);

        assert(contaRecuperada.getChaveUsuario().equals(chaveUsuario));
        assert(contaRecuperada.getTipoConta()==TipoConta.INVESTIMENTO);
    }

    @Test
    public void deveCadastrarContaCorrenteDePJ() {
        Integer chaveUsuario = gerarChaveUsuarioValido(TipoUsuario.JURIDICA,usuarioService);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.CORRENTE);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);

        assert(contaRecuperada.getChaveUsuario().equals(chaveUsuario));
        assert(contaRecuperada.getTipoConta()==TipoConta.CORRENTE);
    }

    @Test
    public void deveCadastrarContaInvestimentoDePJ() {
        Integer chaveUsuario = gerarChaveUsuarioValido(TipoUsuario.JURIDICA,usuarioService);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.INVESTIMENTO);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);

        assert(contaRecuperada.getChaveUsuario().equals(chaveUsuario));
        assert(contaRecuperada.getTipoConta()==TipoConta.INVESTIMENTO);
    }

    @Test
    public void naoDeveCadastrarContaPoupancaDePJ() {
        Integer chaveUsuario = gerarChaveUsuarioValido(TipoUsuario.JURIDICA,usuarioService);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.POUPANCA);

        assertThrows(NegotialException.class,()->contaService.cadastrarValor(contaCorrente)) ;
    }

    @Test
    public void naoDeveCadastrarContaDeUsuarioInexistente() {

        ContaModel contaCorrente = new ContaModel(-1, TipoConta.CORRENTE);

        assertThrows(NoSuchElementException.class,()->contaService.cadastrarValor(contaCorrente)) ;
    }

    @Test
    public void naoDeveRecuperarContaInexistente(){
        assertThrows(NoSuchElementException.class,()->contaService.recuperarValor(-1));
    }

    @Test
    public void naoDeveCadastrarContaCampoVazio(){
        ContaModel contaModel = new ContaModel(null,null);

        assertThrows(CampoVazioException.class,()->contaService.cadastrarValor(contaModel));
    }



}
