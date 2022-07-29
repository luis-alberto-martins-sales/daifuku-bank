package com.daifuku.exceptions;

public class UsuarioDuplicadoException extends NegotialException{
    public UsuarioDuplicadoException(){
        super("Usuário já cadastrado.");
    }
}
