import java.util.Date;

public class TransacaoEmContaDeposito implements TransacaoEmConta {

	private float valor;
	private Date dataOperacao;
	private String tipo;

	public TransacaoEmContaDeposito(float valor, Date dataOperacao, String tipo) {
		this.valor = valor;
		this.dataOperacao = dataOperacao;
		this.tipo = tipo;
	}

	@Override
	public float getValor() {
		return this.valor;
	}

	@Override
	public String getDescricao() {
		return "Valor: "+this.valor+" em "+this.dataOperacao;
	}

	@Override
	public String getTipoOperacao() {
		return this.tipo;
	}

	@Override
	public Date getData() {
		return this.dataOperacao;
	}
	
	@Override
	public String getDetalhes() {
		return 	this.getTipoOperacao() + "\n" +
				"Valor: " + this.valor + "\n"+
				"Data Operação: " + Utilitarios.formatarData(this.dataOperacao) + "\n";
	}

}
