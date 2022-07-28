package com.daifuku.app;

import com.daifuku.conta.ContaDAO;
import com.daifuku.conta.ContaModel;
import com.daifuku.conta.ContaService;
import com.daifuku.enums.TipoConta;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.usuario.*;
import com.daifuku.utils.CNPJ;
import com.daifuku.utils.CPF;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


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
        //TODO verificar todos os campos
        assert(contaRecuperada.getTipoConta()==TipoConta.CORRENTE);
    }

    @Test
    public void deveCadastrarContaPoupancaDePF() {
        Integer chaveUsuario = getChaveUsuarioValido(TipoUsuario.FISICA);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.POUPANCA);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);
        //TODO verificar todos os campos
        assert(contaRecuperada.getTipoConta()==TipoConta.POUPANCA);
    }

    @Test
    public void deveCadastrarContaInvestimentoDePF() {
        Integer chaveUsuario = getChaveUsuarioValido(TipoUsuario.FISICA);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.INVESTIMENTO);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);
        //TODO verificar todos os campos
        assert(contaRecuperada.getTipoConta()==TipoConta.INVESTIMENTO);
    }

    @Test
    public void deveCadastrarContaCorrenteDePJ() {
        Integer chaveUsuario = getChaveUsuarioValido(TipoUsuario.JURIDICA);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.CORRENTE);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);
        //TODO verificar todos os campos
        assert(contaRecuperada.getTipoConta()==TipoConta.CORRENTE);
    }

    @Test
    public void deveCadastrarContaInvestimentoDePJ() {
        Integer chaveUsuario = getChaveUsuarioValido(TipoUsuario.JURIDICA);
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.INVESTIMENTO);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);
        //TODO verificar todos os campos
        assert(contaRecuperada.getTipoConta()==TipoConta.INVESTIMENTO);
    }

    //TODO realizar testes de falha

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
