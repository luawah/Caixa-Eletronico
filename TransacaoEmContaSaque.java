import java.util.Date;

public class TransacaoEmContaSaque implements TransacaoEmConta {
	private float valor;
	private Date dataOperacao;
	
	public TransacaoEmContaSaque(float valor, Date dataOperacao) {
		this.valor = valor;
		this.dataOperacao = dataOperacao;
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
		return "Saque";
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
