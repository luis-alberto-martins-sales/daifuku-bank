package com.daifuku.exceptions;

public class SaldoInsuficienteException extends NegotialException{
    public SaldoInsuficienteException(){
        super("Saldo insuficiente.");
    }
}
