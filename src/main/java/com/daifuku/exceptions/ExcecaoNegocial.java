package com.daifuku.exceptions;

public class ExcecaoNegocial extends RuntimeException{

    public ExcecaoNegocial(String mensagem){
        super(mensagem);
    }
}
