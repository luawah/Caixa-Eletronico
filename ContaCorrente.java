import java.util.Date;

public class ContaCorrente extends Conta {

	public ContaCorrente(float valorPagamento, Date dataPagamento) {
		super(valorPagamento, dataPagamento);
	}

	public ContaCorrente() {
		super();
	}

	@Override
	public String toString() {
		return "CC " + this.getAgencia() + "/" + this.getNumeroConta() + 
				" | Saldo: R$ " + this.getSaldo() + " | Próximo pagamento: "+Utilitarios.formatarData(this.getDataPagamento());
	}

	private final float LIMITE_CHEQUE_ESPECIAL = 3000;


	@Override
	float getValorLimite() {
		return this.LIMITE_CHEQUE_ESPECIAL;
	}

	@Override
	float getValorAtualizacao() {
		return 0; // Conta corrente não tem atualização
	}



}
