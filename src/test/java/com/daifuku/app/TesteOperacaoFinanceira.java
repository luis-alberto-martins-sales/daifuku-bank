package com.daifuku.app;

import com.daifuku.app.utils.TEST_CONSTANTS;
import com.daifuku.conta.ContaDAO;
import com.daifuku.conta.ContaModel;
import com.daifuku.conta.ContaService;
import com.daifuku.enums.TipoConta;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraDAO;
import com.daifuku.operacaoFinanceira.OperacaoFinanceiraService;
import com.daifuku.usuario.*;
import com.daifuku.utils.CNPJ;
import com.daifuku.utils.CPF;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;


public class TesteOperacaoFinanceira {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private UsuarioService usuarioService = new UsuarioService(usuarioDAO);

    private ContaDAO contaDAO =new ContaDAO();
    private ContaService contaService = new ContaService(contaDAO,usuarioDAO);

    private OperacaoFinanceiraDAO operacaoFinanceiraDAO =new OperacaoFinanceiraDAO();
    private OperacaoFinanceiraService operacaoFinanceiraService = new OperacaoFinanceiraService(operacaoFinanceiraDAO,contaDAO,usuarioDAO);


    @Test
    public void deveDepositarMontanteContaCorrenteDePF() {
        Integer chaveUsuario = getChaveUsuarioValido(TipoUsuario.FISICA);
        Integer chaveConta = getChaveContaUsuarioValido(chaveUsuario,TipoConta.CORRENTE);
        BigDecimal montante = new BigDecimal(Math.random()* TEST_CONSTANTS.INTERVALO_MONTANTE).setScale(2, RoundingMode.HALF_EVEN);
        operacaoFinanceiraService.depositar(montante,chaveConta);
        assertEquals(montante,contaService.consultarSaldo(chaveConta));
    }

    @Test
    public void deveSacarMontanteContaCorrenteDePF() {
        Integer chaveUsuario = getChaveUsuarioValido(TipoUsuario.FISICA);
        Integer chaveConta = getChaveContaUsuarioValido(chaveUsuario,TipoConta.CORRENTE);
        BigDecimal montante = new BigDecimal(Math.random()* TEST_CONSTANTS.INTERVALO_MONTANTE).setScale(2, RoundingMode.HALF_EVEN);
        operacaoFinanceiraService.depositar(montante,chaveConta);
        operacaoFinanceiraService.sacar(montante,chaveConta);
        assertEquals(BigDecimal.ZERO.setScale(2),contaService.consultarSaldo(chaveConta));
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

    private Integer getChaveContaUsuarioValido(Integer chaveUsuarioValido,TipoConta tipoConta){
        ContaModel contaCorrente = new ContaModel(chaveUsuarioValido, tipoConta);
        return contaService.cadastrarValor(contaCorrente);
    }

}
