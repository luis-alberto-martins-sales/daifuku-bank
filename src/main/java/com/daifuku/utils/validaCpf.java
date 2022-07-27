package com.daifuku.utils;

import static java.lang.Integer.parseInt;

public class validaCpf {

    //http://www.receita.fazenda.gov.br/aplicacoes/atcta/cpf/funcoes.js
    public static boolean testaCPF(String strCPF) {
        if (!strCPF.matches("\\d{11}")){
            throw new IllegalArgumentException("Cpf inválido.");
        }
        int Soma;
        int Resto;
        Soma = 0;
        //strCPF  = RetiraCaracteresInvalidos(strCPF,11);
        if (strCPF == "00000000000")
            return false;
        for (int i=1; i<=9; i++)
            Soma = Soma + parseInt(strCPF.substring(i-1, i)) * (11 - i);
        Resto = (Soma * 10) % 11;
        if ((Resto == 10) || (Resto == 11))
            Resto = 0;
        if (Resto != parseInt(strCPF.substring(9, 10)) )
            return false;
        Soma = 0;
        for (int i = 1; i <= 10; i++)
            Soma = Soma + parseInt(strCPF.substring(i-1, i)) * (12 - i);
        Resto = (Soma * 10) % 11;
        if ((Resto == 10) || (Resto == 11))
            Resto = 0;
        if (Resto != parseInt(strCPF.substring(10, 11) ) )
            return false;
        return true;
    }


}
