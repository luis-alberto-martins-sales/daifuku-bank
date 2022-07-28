package com.daifuku.app;

import static org.junit.Assert.assertEquals;

import com.daifuku.enums.TipoUsuario;
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
    public void deveCadastrarUsuarioPFValido()  {

        UsuarioModel usuario = getUsuarioValido(TipoUsuario.FISICA);
        Integer chave = usuarioService.cadastrarValor(usuario);
        UsuarioModel usuarioRecuperado = usuarioService.recuperarValor(chave);
        //TODO verificar todos os campos
        assertEquals(usuario.getEmail(),usuarioRecuperado.getEmail());
    }

    @Test
    public void deveCadastrarUsuarioPJValido()  {
        UsuarioModel usuario = getUsuarioValido(TipoUsuario.JURIDICA);
        Integer chave =usuarioService.cadastrarValor(usuario);
        UsuarioModel usuarioRecuperado = usuarioService.recuperarValor(chave);
        //TODO verificar todos os campos
        assertEquals(usuario.getEmail(),usuarioRecuperado.getEmail());
    }

    //TODO realizar testes de falha
    private UsuarioModel getUsuarioValido(TipoUsuario tipo){
        String nome = RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME);
        String email = RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME)+"@"
                +RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME)+"."
                +RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME);
        if (tipo==TipoUsuario.FISICA){
            return new PessoaFisica(nome,email, CPF.gerarCPF());
        }
        return new PessoaJuridica(nome,email, CNPJ.gerarCNPJ());
    }

}
