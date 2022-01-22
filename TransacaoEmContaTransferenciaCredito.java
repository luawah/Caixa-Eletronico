import java.util.Date;

public class TransacaoEmContaTransferenciaCredito implements TransacaoEmConta {

	private float valor;
	private Date dataOperacao;
	private Conta origem;
	
	public TransacaoEmContaTransferenciaCredito(float valor, Date dataOperacao, Conta origem) {
		super();
		this.valor = valor;
		this.dataOperacao = dataOperacao;
		this.origem = origem;
	}

	@Override
	public float getValor() {
		return this.valor;
	}

	@Override
	public String getDescricao() {
		return "De: "+this.origem.getAgencia()+"/"+this.origem.getNumeroConta();
	}

	@Override
	public String getTipoOperacao() {
		return "Transferência a crédito";
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
				"Origem: " + this.origem + "\n";
	}

}
