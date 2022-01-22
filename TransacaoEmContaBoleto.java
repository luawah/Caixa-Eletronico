import java.util.Date;

public class TransacaoEmContaBoleto implements TransacaoEmConta {
	public static float TAXA_MULTA = 0.1f;
	
	private String codigoBarras; // 48 dígitos
	private float valor;
	private Date dataVencimento;
	private Date dataOperacao;
	private float multa = 0;
	
	public float getMulta() {
		return multa;
	}

	public void setMulta(float multa) {
		this.multa = multa;
	}

	public TransacaoEmContaBoleto(String codigoBarras, float valor, Date dataVencimento, Date dataOperacao) {
		this.codigoBarras = codigoBarras;
		this.valor = valor;
		this.dataVencimento = dataVencimento;
		this.dataOperacao = dataOperacao;
	}

	@Override
	public float getValor() {
		return this.valor;
	}

	@Override
	public String getDescricao() {
		return "Vencimento: " + this.dataVencimento + ", Código de Barras: " + this.codigoBarras;
	}

	@Override
	public String getTipoOperacao() {
		return "Boleto";
	}

	@Override
	public Date getData() {
		return this.dataOperacao;
	}

	public Date getDataVencimento() {
		return this.dataVencimento;
	}
	
	@Override
	public String getDetalhes() {
		return this.getTipoOperacao() + "\n" +
				"Código de Barras: " + this.codigoBarras + "\n"+
				"Valor: " + this.valor + "\n"+
				"Multa: " + this.multa + "\n"+
				"Data Vencimento: " + Utilitarios.formatarData(this.dataVencimento) + "\n"+
				"Data Operação: " + Utilitarios.formatarData(this.dataOperacao) + "\n";
	}

}
