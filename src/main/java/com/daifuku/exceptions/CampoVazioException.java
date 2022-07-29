package com.daifuku.exceptions;

public class CampoVazioException extends NegotialException{
    public CampoVazioException(String campo){
        super("O campo de "+campo+" não foi informado.");
    }
}
