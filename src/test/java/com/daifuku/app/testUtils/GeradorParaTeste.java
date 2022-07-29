package com.daifuku.app.testUtils;

import com.daifuku.conta.ContaModel;
import com.daifuku.conta.ContaService;
import com.daifuku.enums.TipoConta;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.usuario.PessoaFisica;
import com.daifuku.usuario.PessoaJuridica;
import com.daifuku.usuario.UsuarioModel;
import com.daifuku.usuario.UsuarioService;
import com.daifuku.utils.CNPJ;
import com.daifuku.utils.CPF;
import org.apache.commons.lang.RandomStringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeradorParaTeste {

    public static UsuarioModel gerarUsuarioValido(TipoUsuario tipo){
        String nome = gerarNomeValido();
        String email = gerarEmailValido();
        if (tipo==TipoUsuario.FISICA){
            return new PessoaFisica(nome,email, CPF.gerarCPF());
        }
        return new PessoaJuridica(nome,email, CNPJ.gerarCNPJ());
    }

    public static Integer gerarChaveUsuarioValido(TipoUsuario tipo, UsuarioService usuarioService) {
                return usuarioService.cadastrarValor(gerarUsuarioValido(tipo));
    }

    public static String gerarEmailValido(){
        return RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME)+"@"
                +RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME)+"."
                +RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME);
    }

    public static String gerarNomeValido(){
        return RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME);
    }

    public static Integer gerarChaveContaUsuarioValido(Integer chaveUsuarioValido, TipoConta tipoConta, ContaService contaService){
        ContaModel conta = new ContaModel(chaveUsuarioValido, tipoConta);
        return contaService.cadastrarValor(conta);
    }

    public static Integer gerarChaveContaUsuario(TipoUsuario tipoUsuario, TipoConta tipoConta, ContaService contaService, UsuarioService usuarioService){
        Integer chaveUsuarioValido = gerarChaveUsuarioValido(tipoUsuario, usuarioService);
        ContaModel conta = new ContaModel(chaveUsuarioValido, tipoConta);
        return contaService.cadastrarValor(conta);
    }

    public static BigDecimal gerarMontante(){
        return new BigDecimal(Math.random()* TEST_CONSTANTS.INTERVALO_MONTANTE).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal gerarMontanteInferiorA(BigDecimal montante){
        return new BigDecimal(Math.random()* montante.doubleValue()).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal gerarMontanteSuperiorA(BigDecimal montante){
        return new BigDecimal(Math.random()*TEST_CONSTANTS.INTERVALO_MONTANTE+montante.doubleValue()).setScale(2, RoundingMode.HALF_EVEN);
    }
}
