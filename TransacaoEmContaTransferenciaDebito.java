import java.util.Date;

public class TransacaoEmContaTransferenciaDebito implements TransacaoEmConta {
	
	private float valor;
	private Date dataOperacao;
	private Conta destino;
	
	public TransacaoEmContaTransferenciaDebito(float valor, Date dataOperacao, Conta destino) {
		this.valor = valor;
		this.dataOperacao = dataOperacao;
		this.destino = destino;
	}

	@Override
	public float getValor() {
		return this.valor;
	}

	@Override
	public String getDescricao() {
		return "Para: "+this.destino.getAgencia()+"/"+this.destino.getNumeroConta();
	}

	@Override
	public String getTipoOperacao() {
		return "Transferência a débito";
	}

	@Override
	public Date getData() {
		return this.dataOperacao;
	}


	@Override
	public String getDetalhes() {
		return 	this.getTipoOperacao() + "\n" +
				"Valor: " + this.valor + "\n"+
				"Data Operação: " + Utilitarios.formatarData(this.dataOperacao) + "\n"+
				"Destino: " + this.destino + "\n";
	}
}
