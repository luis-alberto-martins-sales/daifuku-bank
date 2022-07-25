package main.java.com.daifuku.models;

import java.util.List;
import java.util.Set;

import main.java.com.daifuku.enums.TipoUsuario;

public abstract class Usuario {
    String nome;
    TipoUsuario tipoUsuario;
    Set<Conta> contas;
}
