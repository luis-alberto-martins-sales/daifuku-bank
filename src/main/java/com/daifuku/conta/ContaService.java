package com.daifuku.conta;

import com.daifuku.arquitetura.Service;
import com.daifuku.constants.RENDIMENTO_MENSAL;
import com.daifuku.enums.TipoConta;
import com.daifuku.enums.TipoUsuario;
import com.daifuku.exceptions.ExcecaoNegocial;
import com.daifuku.arquitetura.DAOInterface;
import com.daifuku.usuario.UsuarioModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class ContaService extends Service<ContaModel> {

    private final DAOInterface usuarioDAO;

    public ContaService(DAOInterface contaDAO, DAOInterface usuarioDAO) {
        super(contaDAO);
        this.usuarioDAO=usuarioDAO;
    }

    protected void validarValor(ContaModel contaModel) {
        UsuarioModel usuario;
        usuario= (UsuarioModel) this.usuarioDAO.recuperarValor(contaModel.getChaveUsuario());

        if (usuario.getTipoUsuario()== TipoUsuario.JURIDICA
                && contaModel.getTipoConta()== TipoConta.POUPANCA){
            throw new ExcecaoNegocial("Pessoa jurídica não pode abrir conta poupança.");
        }
    }

    @Override
    protected void verificarCampoVazio(ContaModel contaModel) {
        if (contaModel.getChaveUsuario()==null){
            throw new IllegalArgumentException("Chave de usuário titular da conta não informado.");
        }
        if (contaModel.getTipoConta()==null){
            throw new IllegalArgumentException("Tipo de conta não informado.");
        }
    }

    public BigDecimal consultarSaldo(Integer chaveConta) {
        return ((ContaDAO) DAO).consultarSaldo(chaveConta);
    }


    public BigDecimal consultarRendimentoFuturo(Integer chaveConta, LocalDateTime dataFutura) {
        validarConsultaRendimentoFuturo(chaveConta, dataFutura);
        int totalMeses = (int) ChronoUnit.MONTHS.between(dataFutura,LocalDateTime.now());
        //FIXME
        if (((UsuarioModel) usuarioDAO.recuperarValor(DAO.recuperarValor(chaveConta).getChaveUsuario())).getTipoUsuario()==TipoUsuario.FISICA){
            return consultarSaldo(chaveConta).multiply(BigDecimal.ONE.add(RENDIMENTO_MENSAL.PF).pow(totalMeses)).setScale(2, RoundingMode.HALF_DOWN);
        }
        return consultarSaldo(chaveConta).multiply(BigDecimal.ONE.add(RENDIMENTO_MENSAL.PJ).pow(totalMeses)).setScale(2, RoundingMode.HALF_DOWN);
    }

    private void validarConsultaRendimentoFuturo(Integer chaveConta, LocalDateTime dataFutura) {
        if (dataFutura.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Data informada deve ser posterior a "+ dataFutura +".");
        }
        if (consultarSaldo(chaveConta).signum()!=1){
            throw new ExcecaoNegocial("Saldo da conta deve ter valor positivo.");
        }
        if (DAO.recuperarValor(chaveConta).getTipoConta()==TipoConta.CORRENTE){
            throw new ExcecaoNegocial("Conta corrente não é rentabilizada.");
        }
    }
}
