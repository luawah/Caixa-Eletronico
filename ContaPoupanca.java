import java.util.Date;

public class ContaPoupanca extends Conta {
	private final float VALOR_ATUALIZACAO = 0.3f;
	
	private Date dataProximaAtualizacao;
	
	public ContaPoupanca(float valorPagamento, Date dataPagamento) {
		super(valorPagamento, dataPagamento, Utilitarios.somarMesesData(new Date(),1));
	}
	public ContaPoupanca() {
		super(Utilitarios.somarMesesData(new Date(),1));
	}

	@Override
	public String toString() {
		return "CP " + this.getAgencia() + "/" + this.getNumeroConta() + 
				" | Saldo: R$ " + this.getSaldo() + " | Próximo rendimento: "+Utilitarios.formatarData(this.getDataProximaAtualizacao());
	}

	@Override
	float getValorLimite() {
		return 0; // conta poupança não tem limite de cheque especial
	}

	@Override
	float getValorAtualizacao() {
		return this.VALOR_ATUALIZACAO/100;
	}


}
