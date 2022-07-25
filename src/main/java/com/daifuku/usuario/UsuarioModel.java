package main.java.com.daifuku.usuario;

import java.util.List;
import java.util.Set;

import main.java.com.daifuku.enums.TipoUsuario;

public abstract class UsuarioModel {
    String nome;
    TipoUsuario tipoUsuario;
    Set<Conta> contas;
}
