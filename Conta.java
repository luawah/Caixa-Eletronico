import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public abstract class Conta {
	protected static int contador = 1; // usado para gerar número da conta
	protected static Date dataAtual =  new Date();
	
	private String agencia = "0001";
	private int numero;
	private float valorPagamento = 0;
	private Date dataPagamento = null;
	private Date dataProximaAtualizacao;
	private float saldo = 0;
	
	
	public Date getDataPagamento() {
		return dataPagamento;
	}

	public Date getDataProximaAtualizacao() {
		return dataProximaAtualizacao;
	}

	
	// O sistema deve permitir configurar o PIX, definindo qual informação será
	// utilizada para transferência (cpf, e-mail e telefone ou criando uma chave
	// nova);
	private String chavePix;

	public ArrayList<TransacaoEmConta> transacoes = new ArrayList<TransacaoEmConta>();
	

	// construtores para contas sem rendimento
	
	public Conta(float valorPagamento, Date dataPagamento) {
		this();
		this.valorPagamento = valorPagamento;
		this.dataPagamento = dataPagamento;
	}
	
	public Conta() {
		this.numero = contador++;
	}

	
	// construtores para contas que tem rendimento
	
	public Conta(float valorPagamento, Date dataPagamento, Date proximaAtualizacao) {
		this(valorPagamento, dataPagamento);
		this.dataProximaAtualizacao = proximaAtualizacao;
	}
	
	public Conta(Date proximaAtualizacao) {
		this();
		this.dataProximaAtualizacao = proximaAtualizacao;
	}
	
	
	public static void AvancarData(int qtdDias, ArrayList<Pessoa> clientes) throws Exception
	{
		Date auxData = (Date) Conta.dataAtual;

        for(int i=0;i<qtdDias;i++)
        {     	
        	auxData = Utilitarios.somarDiasData(auxData,1);

       	
        	for (Pessoa p : clientes)
    		{
    			for (Conta conta : p.contas)
    			{
    				if (conta.dataPagamento!=null)
    				{
    					if (conta.dataPagamento.equals(auxData))
    					{   
    						conta.transacoes.add(new TransacaoEmContaDeposito(conta.valorPagamento, new Date(), "Salário"));
    						conta.dataPagamento = Utilitarios.somarMesesData(conta.dataPagamento,1);
    						conta.saldo += conta.valorPagamento;
    					}
    				}
					if (conta.getValorAtualizacao()>0)
					{
						if (conta.dataProximaAtualizacao.equals(auxData))
						{
    						float valor = conta.getSaldo()*conta.getValorAtualizacao();
    						conta.transacoes.add(new TransacaoEmContaDeposito(valor, new Date(), "Rendimento"));
    						conta.dataProximaAtualizacao = Utilitarios.somarMesesData(conta.dataProximaAtualizacao,1);
    						conta.saldo += valor;
    					}
    				}
    			}
    		}
        }
        Conta.dataAtual = auxData;
	}


	// método que retorna o limite da conta e deverá ser implementado nas classes filhas
	// desta forma, pode-se deixar a implementação do sacar e depositar nesta clase abstrata
	abstract float getValorLimite();

	// método que retorna o valor de atualização da conta e deverá ser implementado nas classes filhas
	// desta forma, pode-se deixar a implementação do atualizar saldo nesta clase abstrata
	abstract float getValorAtualizacao();
	
	// O sistema deve atualizar	mensalmente o saldo da conta poupança com o rendimento de 0,3%
	public void AtualizarSaldo()
	{
		this.saldo += this.saldo*this.getValorAtualizacao(); 
	}
	
	public void Sacar(float valor) throws Exception
	{
		if (valor<=0)
			throw new Exception("Impossível sacar um valor menor ou igual a zero!");
		
		if (this.saldo + this.getValorLimite() < valor)
			throw new Exception("Saldo insuficiente para sacar o valor desejado!");
		
		this.transacoes.add(new TransacaoEmContaSaque(valor, new Date()));
		this.saldo -= valor;
	}
	
	public void Depositar(float valor) throws Exception
	{
		if (valor<=0)
			throw new Exception("Impossível depositar um valor menor ou igual a zero!");
	
		this.transacoes.add(new TransacaoEmContaDeposito(valor, new Date(), "Depósito"));
		this.saldo += valor;
	}
	
	public void Transferir(float valor, Conta destino) throws Exception
	{
		this.Sacar(valor);
		destino.Depositar(valor);
		
		this.transacoes.add(new TransacaoEmContaTransferenciaDebito(valor,new Date(),destino));
		destino.transacoes.add(new TransacaoEmContaTransferenciaCredito(valor,new Date(),this));
	}
	
	
	// fonte: https://www.baeldung.com/java-date-difference
	public void PagarBoleto(TransacaoEmContaBoleto boleto) throws Exception
	{
		long diff = Utilitarios.calcularDiferenteEmDias(boleto.getData(), boleto.getDataVencimento());

		float valor = boleto.getValor();
		if (diff>0)
		{
			// fonte: https://brasilescola.uol.com.br/matematica/juros-compostos.htm
			valor += (float) valor*TransacaoEmContaBoleto.TAXA_MULTA/100*diff;
			boleto.setMulta(valor-boleto.getValor());
		}
		this.saldo -= valor;
		this.transacoes.add(boleto);
	}

	public String getAgencia() {
		return this.agencia;
	}

	public int getNumeroConta() {
		return this.numero;
	}


	public String getChavePix() {
		return chavePix;
	}

	public void setChavePix(String chavePix) {
		this.chavePix = chavePix;
	}
	
	public float getSaldo() {
		return this.saldo;
	}
	
	// fonte: https://www.baeldung.com/java-random-string
	public void gerarChavePixAleatoria() {
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    this.chavePix = generatedString;
	}

}
