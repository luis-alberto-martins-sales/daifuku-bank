package com.daifuku.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.daifuku.app.utils.TEST_CONSTANTS;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.exceptions.NegotialException;
import com.daifuku.exceptions.UsuarioDuplicadoException;
import com.daifuku.usuario.*;
import com.daifuku.utils.CNPJ;
import com.daifuku.utils.CPF;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.util.NoSuchElementException;


public class TesteUsuario
{
    private UsuarioDAO usuarioDAO=new UsuarioDAO();
    private UsuarioService usuarioService= new UsuarioService(usuarioDAO);


    @Test
    public void deveCadastrarUsuarioPFValido()  {

        UsuarioModel usuario = getUsuarioValido(TipoUsuario.FISICA);
        Integer chave = usuarioService.cadastrarValor(usuario);
        UsuarioModel usuarioRecuperado = usuarioService.recuperarValor(chave);

        assertEquals(usuario.getNome(),usuarioRecuperado.getNome());
        assertEquals(usuario.getEmail(),usuarioRecuperado.getEmail());
        assertEquals(((PessoaFisica) usuario).getCpf(),((PessoaFisica) usuarioRecuperado).getCpf());
    }

    @Test
    public void deveCadastrarUsuarioPJValido()  {
        UsuarioModel usuario = getUsuarioValido(TipoUsuario.JURIDICA);
        Integer chave =usuarioService.cadastrarValor(usuario);
        UsuarioModel usuarioRecuperado = usuarioService.recuperarValor(chave);

        assertEquals(usuario.getNome(),usuarioRecuperado.getNome());
        assertEquals(usuario.getEmail(),usuarioRecuperado.getEmail());
        assertEquals(((PessoaJuridica) usuario).getCnpj(),((PessoaJuridica) usuarioRecuperado).getCnpj());
    }

    //TODO realizar testes de falha
    @Test
    public void naoDeveCadastrarUsuarioDuplicado()  {
        UsuarioModel usuario = getUsuarioValido(TipoUsuario.FISICA);
        usuarioService.cadastrarValor(usuario);
        assertThrows(NegotialException.class,
                () -> usuarioService.cadastrarValor(usuario));
    }

    @Test
    public void naoDeveCadastrarUsuarioComEmailJaCadastrado()  {
        UsuarioModel usuario = getUsuarioValido(TipoUsuario.FISICA);
        usuarioService.cadastrarValor(usuario);
        UsuarioModel novoUsuario = new PessoaFisica(usuario.getNome(), usuario.getEmail(), CPF.gerarCPF());
        assertThrows(UsuarioDuplicadoException.class,
                () -> usuarioService.cadastrarValor(novoUsuario));
    }

    @Test
    public void naoDeveCadastrarUsuarioComCpfJaCadastrado()  {
        UsuarioModel usuario = getUsuarioValido(TipoUsuario.FISICA);
        usuarioService.cadastrarValor(usuario);
        String email = RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME)+"@"
                +RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME)+"."
                +RandomStringUtils.randomAlphanumeric(TEST_CONSTANTS.COMPRIMENTO_NOME);
        UsuarioModel novoUsuario = new PessoaFisica(usuario.getNome(), email, ((PessoaFisica) usuario).getCpf());
        assertThrows(UsuarioDuplicadoException.class,
                () -> usuarioService.cadastrarValor(novoUsuario));
    }

    @Test
    public void naoDeveRecuperarUsuarioInexistente(){
        assertThrows(NoSuchElementException.class,()->usuarioService.recuperarValor(-1));
    }

    @Test
    public void naoDeveCadastrarUsuarioCampoVazio(){
        UsuarioModel usuarioModel = new PessoaFisica("","","");
        assertThrows(IllegalArgumentException.class,()->usuarioService.cadastrarValor(usuarioModel));
    }

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
