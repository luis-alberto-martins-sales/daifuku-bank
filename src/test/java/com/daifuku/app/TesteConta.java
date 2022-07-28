package com.daifuku.app;

import com.daifuku.conta.ContaDAO;
import com.daifuku.conta.ContaModel;
import com.daifuku.conta.ContaService;
import com.daifuku.enums.TipoConta;
import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.usuario.*;
import com.daifuku.utils.CNPJ;
import com.daifuku.utils.CPF;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TesteConta
{
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private UsuarioService usuarioService = new UsuarioService(usuarioDAO);

    private ContaDAO contaDAO =new ContaDAO();
    private ContaService contaService = new ContaService(contaDAO,usuarioDAO);


    @Test
    public void deveCadastrarContaCorrenteDePF() throws ExcecaoNegocial {
        Integer chaveUsuario = getChaveUsuarioPFValido();
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.CORRENTE);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);
        //TODO verificar todos os campos
        assert(contaRecuperada.getTipoConta()==TipoConta.CORRENTE);
    }

    @Test
    public void deveCadastrarContaPoupancaDePF() throws ExcecaoNegocial {
        Integer chaveUsuario = getChaveUsuarioPFValido();
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.POUPANCA);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);
        //TODO verificar todos os campos
        assert(contaRecuperada.getTipoConta()==TipoConta.POUPANCA);
    }

    @Test
    public void deveCadastrarContaInvestimentoDePF() throws ExcecaoNegocial {
        Integer chaveUsuario = getChaveUsuarioPFValido();
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.INVESTIMENTO);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);
        //TODO verificar todos os campos
        assert(contaRecuperada.getTipoConta()==TipoConta.INVESTIMENTO);
    }

    @Test
    public void deveCadastrarContaCorrenteDePJ() throws ExcecaoNegocial {
        Integer chaveUsuario = getChaveUsuarioPJValido();
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.CORRENTE);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);
        //TODO verificar todos os campos
        assert(contaRecuperada.getTipoConta()==TipoConta.CORRENTE);
    }

    @Test
    public void deveCadastrarContaInvestimentoDePJ() throws ExcecaoNegocial {
        Integer chaveUsuario = getChaveUsuarioPJValido();
        ContaModel contaCorrente = new ContaModel(chaveUsuario, TipoConta.INVESTIMENTO);
        Integer chaveConta = contaService.cadastrarValor(contaCorrente);
        ContaModel contaRecuperada = contaService.recuperarValor(chaveConta);
        //TODO verificar todos os campos
        assert(contaRecuperada.getTipoConta()==TipoConta.INVESTIMENTO);
    }

    //TODO realizar testes de falha

    private Integer getChaveUsuarioPFValido() throws ExcecaoNegocial {
        String nome = RandomStringUtils.randomAlphanumeric(10);
        String email = RandomStringUtils.randomAlphanumeric(10)+"@"
                +RandomStringUtils.randomAlphanumeric(10)+"."
                +RandomStringUtils.randomAlphanumeric(10);
        UsuarioModel usuario = new PessoaFisica(nome,email, CPF.gerarCPF());
        return usuarioService.cadastrarValor(usuario);
    }

    private Integer getChaveUsuarioPJValido() throws ExcecaoNegocial {
        String nome = RandomStringUtils.randomAlphanumeric(10);
        String email = RandomStringUtils.randomAlphanumeric(10)+"@"
                +RandomStringUtils.randomAlphanumeric(10)+"."
                +RandomStringUtils.randomAlphanumeric(10);
        UsuarioModel usuario = new PessoaJuridica(nome,email, CNPJ.gerarCNPJ());
        return usuarioService.cadastrarValor(usuario);
    }
}
