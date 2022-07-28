package com.daifuku.conta;

import com.daifuku.arquitetura.Service;
import com.daifuku.constants.RENDIMENTO;
import com.daifuku.enums.TipoConta;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.arquitetura.DAOInterface;
import com.daifuku.usuario.UsuarioModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;


public class ContaService extends Service<ContaModel> {

    private final DAOInterface usuarioDAO;

    public ContaService(DAOInterface contaDAO, DAOInterface usuarioDAO) {
        super(contaDAO);
        this.usuarioDAO=usuarioDAO;
    }

    protected void validarValor(ContaModel contaModel) throws ExcecaoNegocial {
        UsuarioModel usuario;
        usuario= (UsuarioModel) this.usuarioDAO.recuperarValor(contaModel.getChaveUsuario());

        if (usuario.getTipoUsuario()== TipoUsuario.JURIDICA
                && contaModel.getTipoConta()== TipoConta.POUPANCA){
            throw new ExcecaoNegocial("Pessoa jurídica não pode abrir conta poupança.");
        }
    }
    @Override
    protected void verificarValorVazio(ContaModel contaModel) {
        if (contaModel==null){
            throw new IllegalArgumentException();
        }
        if (contaModel.getChaveUsuario()==null){
            throw new IllegalArgumentException();
        }
        if (contaModel.getTipoConta()==null){
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal consultarSaldo(Integer chaveConta) {
        return ((ContaDAO) DAO).consultarSaldo(chaveConta);
    }

    //FIXME
    public BigDecimal consultarRendimentoFuturo(Integer chaveConta, LocalDateTime dataFutura) throws ExcecaoNegocial {
        if (dataFutura.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Data informada deve ser posterior a "+dataFutura);
        }
        if (consultarSaldo(chaveConta).signum()!=1){
            throw new ExcecaoNegocial("Saldo da conta deve ter valor positivo");
        }
        if (DAO.recuperarValor(chaveConta).getTipoConta()==TipoConta.CORRENTE){
            throw new ExcecaoNegocial("Conta corrente não é rentabilizada.");
        }
        if (((UsuarioModel) usuarioDAO.recuperarValor(DAO.recuperarValor(chaveConta).getChaveUsuario())).getTipoUsuario()==TipoUsuario.FISICA){
            return consultarSaldo(chaveConta).multiply(BigDecimal.ONE.add(RENDIMENTO.PF).pow(dataFutura.get(ChronoField.YEAR)));
        }
        return consultarSaldo(chaveConta).multiply(BigDecimal.ONE.add(RENDIMENTO.PJ).pow(dataFutura.get(ChronoField.YEAR)));
    }
}
