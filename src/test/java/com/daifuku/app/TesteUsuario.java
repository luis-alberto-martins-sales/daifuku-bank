package com.daifuku.app;

import static com.daifuku.app.testUtils.GeradorParaTeste.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.daifuku.enums.TipoUsuario;
import com.daifuku.exceptions.CampoVazioException;
import com.daifuku.exceptions.NegotialException;
import com.daifuku.exceptions.UsuarioDuplicadoException;
import com.daifuku.usuario.*;
import com.daifuku.utils.CPF;
import org.junit.Test;

import java.util.NoSuchElementException;


public class TesteUsuario
{
    private UsuarioDAO usuarioDAO=new UsuarioDAO();
    private UsuarioService usuarioService= new UsuarioService(usuarioDAO);


    @Test
    public void deveCadastrarUsuarioPFValido()  {

        UsuarioModel usuario = gerarUsuarioValido(TipoUsuario.FISICA);
        Integer chave = usuarioService.cadastrarValor(usuario);
        UsuarioModel usuarioRecuperado = usuarioService.recuperarValor(chave);

        assertEquals(usuario.getNome(),usuarioRecuperado.getNome());
        assertEquals(usuario.getEmail(),usuarioRecuperado.getEmail());
        assertEquals(((PessoaFisica) usuario).getCpf(),((PessoaFisica) usuarioRecuperado).getCpf());
    }

    @Test
    public void deveCadastrarUsuarioPJValido()  {
        UsuarioModel usuario = gerarUsuarioValido(TipoUsuario.JURIDICA);
        Integer chave =usuarioService.cadastrarValor(usuario);
        UsuarioModel usuarioRecuperado = usuarioService.recuperarValor(chave);

        assertEquals(usuario.getNome(),usuarioRecuperado.getNome());
        assertEquals(usuario.getEmail(),usuarioRecuperado.getEmail());
        assertEquals(((PessoaJuridica) usuario).getCnpj(),((PessoaJuridica) usuarioRecuperado).getCnpj());
    }

    @Test
    public void naoDeveCadastrarUsuarioDuplicado()  {
        UsuarioModel usuario = gerarUsuarioValido(TipoUsuario.FISICA);
        usuarioService.cadastrarValor(usuario);

        assertThrows(NegotialException.class, () -> usuarioService.cadastrarValor(usuario));
    }

    @Test
    public void naoDeveCadastrarUsuarioComEmailJaCadastrado()  {
        UsuarioModel usuario = gerarUsuarioValido(TipoUsuario.FISICA);
        usuarioService.cadastrarValor(usuario);
        UsuarioModel novoUsuario = new PessoaFisica(usuario.getNome(), usuario.getEmail(), CPF.gerarCPF());

        assertThrows(UsuarioDuplicadoException.class, () -> usuarioService.cadastrarValor(novoUsuario));
    }

    @Test
    public void naoDeveCadastrarUsuarioComCpfJaCadastrado()  {
        UsuarioModel usuario = gerarUsuarioValido(TipoUsuario.FISICA);
        usuarioService.cadastrarValor(usuario);
        String email = gerarEmailValido();
        UsuarioModel novoUsuario = new PessoaFisica(usuario.getNome(), email, ((PessoaFisica) usuario).getCpf());

        assertThrows(UsuarioDuplicadoException.class, () -> usuarioService.cadastrarValor(novoUsuario));
    }

    @Test
    public void naoDeveCadastrarUsuarioComCnpjJaCadastrado()  {
        UsuarioModel usuario = gerarUsuarioValido(TipoUsuario.JURIDICA);
        usuarioService.cadastrarValor(usuario);
        String email = gerarEmailValido();
        UsuarioModel novoUsuario = new PessoaJuridica(usuario.getNome(), email, ((PessoaJuridica) usuario).getCnpj());

        assertThrows(UsuarioDuplicadoException.class, () -> usuarioService.cadastrarValor(novoUsuario));
    }

    @Test
    public void naoDeveRecuperarUsuarioInexistente(){
        assertThrows(NoSuchElementException.class,()->usuarioService.recuperarValor(-1));
    }

    @Test
    public void naoDeveCadastrarUsuarioCampoVazio(){
        UsuarioModel usuario = new PessoaFisica("","","");

        assertThrows(CampoVazioException.class,()->usuarioService.cadastrarValor(usuario));
    }

    @Test
    public void naoDeveCadastrarUsuarioEmailVazio(){
        UsuarioModel usuario = new PessoaFisica(gerarNomeValido(),"","");

        assertThrows(CampoVazioException.class,()->usuarioService.cadastrarValor(usuario));
    }

    @Test
    public void naoDeveCadastrarUsuarioEmailInvalido(){
        UsuarioModel usuario = new PessoaFisica(gerarNomeValido(),gerarNomeValido(),CPF.gerarCPF());

        assertThrows(IllegalArgumentException.class,()->usuarioService.cadastrarValor(usuario));
    }

    @Test
    public void naoDeveCadastrarUsuarioCpfVazio(){
        UsuarioModel usuario = new PessoaFisica(gerarNomeValido(),gerarEmailValido(),"");

        assertThrows(CampoVazioException.class,()->usuarioService.cadastrarValor(usuario));
    }

    @Test
    public void naoDeveCadastrarUsuarioCpfInvalido(){
        UsuarioModel usuario = new PessoaFisica(gerarNomeValido(),gerarEmailValido(),gerarNomeValido());

        assertThrows(IllegalArgumentException.class,()->usuarioService.cadastrarValor(usuario));
    }

}
