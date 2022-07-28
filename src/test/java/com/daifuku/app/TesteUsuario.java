package com.daifuku.app;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.usuario.*;
import com.daifuku.utils.CNPJ;
import com.daifuku.utils.CPF;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;


public class TesteUsuario
{
    private UsuarioDAO usuarioDAO=new UsuarioDAO();
    private UsuarioService usuarioService= new UsuarioService(usuarioDAO);


    @Test
    public void deveCadastrarUsuarioPFValido() throws ExcecaoNegocial {

        UsuarioModel usuario = getUsuarioPFValido();
        Integer chave = usuarioService.cadastrarValor(usuario);
        UsuarioModel usuarioRecuperado = usuarioService.recuperarValor(chave);
        //TODO verificar todos os campos
        assertEquals(usuario.getEmail(),usuarioRecuperado.getEmail());
    }

    @Test
    public void deveCadastrarUsuarioPJValido() throws ExcecaoNegocial {
        UsuarioModel usuario = getUsuarioPJValido();
        Integer chave =usuarioService.cadastrarValor(usuario);
        UsuarioModel usuarioRecuperado = usuarioService.recuperarValor(chave);
        //TODO verificar todos os campos
        assertEquals(usuario.getEmail(),usuarioRecuperado.getEmail());
    }

    //TODO realizar testes de falha
    private UsuarioModel getUsuarioPFValido(){
        String nome = RandomStringUtils.randomAlphanumeric(10);
        String email = RandomStringUtils.randomAlphanumeric(10)+"@"
                +RandomStringUtils.randomAlphanumeric(10)+"."
                +RandomStringUtils.randomAlphanumeric(10);
        return new PessoaFisica(nome,email, CPF.gerarCPF());
    }

    private UsuarioModel getUsuarioPJValido(){
        String nome = RandomStringUtils.randomAlphanumeric(10);
        String email = RandomStringUtils.randomAlphanumeric(10)+"@"
                +RandomStringUtils.randomAlphanumeric(10)+"."
                +RandomStringUtils.randomAlphanumeric(10);
        return new PessoaJuridica(nome,email, CNPJ.gerarCNPJ());
    }
}
