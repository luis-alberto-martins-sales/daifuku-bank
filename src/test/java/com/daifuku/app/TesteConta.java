package com.daifuku.app;

import com.daifuku.app.utils.TEST_CONSTANTS;
import com.daifuku.conta.ContaDAO;
import com.daifuku.conta.ContaModel;
import com.daifuku.conta.ContaService;
import com.daifuku.enums.TipoConta;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.exceptions.NegotialException;
import com.daifuku.usuario.*;
import com.daifuku.utils.CNPJ;
import com.daifuku.utils.CPF;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TesteConta {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private UsuarioService usuarioService = new UsuarioService(usuarioDAO);

    private ContaDAO contaDAO =new ContaDAO();
    private ContaService contaService = new ContaService(contaDAO,usuarioDAO);


    @Test
    public void deveCadastrarContaCorrenteDePF() {
        Integer chaveUsuario = getChaveUsuarioValido(TipoUsuario.FISICA);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.CORRENTE);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);
        assert(contaRecuperada.getTipoConta()==TipoConta.CORRENTE);
        assert(contaRecuperada.getChaveUsuario().equals(chaveUsuario));
    }

    @Test
    public void deveCadastrarContaPoupancaDePF() {
        Integer chaveUsuario = getChaveUsuarioValido(TipoUsuario.FISICA);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.POUPANCA);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);

        assert(contaRecuperada.getChaveUsuario().equals(chaveUsuario));
        assert(contaRecuperada.getTipoConta()==TipoConta.POUPANCA);
    }

    @Test
    public void deveCadastrarContaInvestimentoDePF() {
        Integer chaveUsuario = getChaveUsuarioValido(TipoUsuario.FISICA);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.INVESTIMENTO);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);
        assert(contaRecuperada.getChaveUsuario().equals(chaveUsuario));
        assert(contaRecuperada.getTipoConta()==TipoConta.INVESTIMENTO);
    }

    @Test
    public void deveCadastrarContaCorrenteDePJ() {
        Integer chaveUsuario = getChaveUsuarioValido(TipoUsuario.JURIDICA);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.CORRENTE);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);
        assert(contaRecuperada.getChaveUsuario().equals(chaveUsuario));
        assert(contaRecuperada.getTipoConta()==TipoConta.CORRENTE);
    }

    @Test
    public void deveCadastrarContaInvestimentoDePJ() {
        Integer chaveUsuario = getChaveUsuarioValido(TipoUsuario.JURIDICA);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.INVESTIMENTO);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);
        assert(contaRecuperada.getChaveUsuario().equals(chaveUsuario));
        assert(contaRecuperada.getTipoConta()==TipoConta.INVESTIMENTO);
    }

    @Test
    public void naoDeveCadastrarContaPoupancaDePJ() {
        Integer chaveUsuario = getChaveUsuarioValido(TipoUsuario.JURIDICA);
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
        assertThrows(IllegalArgumentException.class,()->contaService.cadastrarValor(contaModel));
    }

    private Integer getChaveUsuarioValido(TipoUsuario tipo) {
        String nome = RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME);
        String email = RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME)+"@"
                +RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME)+"."
                +RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME);
        UsuarioModel usuario;
        if (tipo== TipoUsuario.FISICA){
            usuario = new PessoaFisica(nome,email, CPF.gerarCPF());
        } else {
            usuario = new PessoaJuridica(nome,email, CNPJ.gerarCNPJ());
        }
        return usuarioService.cadastrarValor(usuario);
    }

}
