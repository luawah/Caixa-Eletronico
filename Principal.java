import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Principal {
	
	private static Scanner sc = new Scanner(System.in);
	private static ArrayList<Pessoa> clientes = new ArrayList<Pessoa>();
	
	// método que busca em todas em todos os clientes por um determinado CPF
	public static Pessoa getPessoaPorCPF(String cpf)
	{
		for (Pessoa p : clientes)
			if (p.getCPF().equals(cpf))
				return p;
		return null;
	}

	// método que busca em todas em todas as contas de todos os clientes por uma chave pix
	public static Conta getContaPorPIX(String pix)
	{
		for (Pessoa p : clientes)
			for (Conta c : p.contas)
				if (c.getChavePix()!=null)
					if (c.getChavePix().equals(pix))
						return c;
		return null;
	}

	// método que busca em todas em todas as contas de todos os clientes por um número
	public static Conta getContaPorNumero(int numero)
	{
		for (Pessoa p : clientes)
			for (Conta c : p.contas)
				if (c.getNumeroConta()==numero)
					return c;
		return null;
	}
	
	public static void abrirConta() throws ParseException {
		String cpfPesq, auxNome, auxEmail, auxTelefone, auxSenha, auxDataNascimento, auxDataPagamento;
		float auxValorPagamento; 
		int tipo, contaSalario;
		Pessoa auxPessoa;
		Conta auxConta;
		
		System.out.print("Informe o CPF do cliente: ");
		cpfPesq = sc.next();
		
		auxPessoa = getPessoaPorCPF(cpfPesq);
		if (auxPessoa==null)
		{
			// cadastrar pessoa
			System.out.print("Nome: ");
			auxNome = sc.next();

			System.out.print("Email: ");
			auxEmail = sc.next();
			
			System.out.print("Telefone: ");
			auxTelefone = sc.next();
			
			System.out.print("Senha: ");
			auxSenha = sc.next();
			
			System.out.print("Data de nascimento: ");
			auxDataNascimento = sc.next();
			
			auxPessoa = new Pessoa(auxNome, cpfPesq, Utilitarios.converterData(auxDataNascimento), auxEmail, auxTelefone, auxSenha);
			clientes.add(auxPessoa);
		}


		// cadastrar conta
		System.out.println();
		System.out.println("Informe o tipo da conta");
		System.out.println("(1) Conta corrente");
		System.out.println("(2) Conta conta poupanca");
		tipo = sc.nextInt();
		
		
		System.out.println();
		System.out.println("Informe uma opção:");
		System.out.println("(1) Conta Salário");
		System.out.println("(2) Conta Normal");
		contaSalario = sc.nextInt();
		
		auxValorPagamento = 0;
		auxDataPagamento = null;
		if (contaSalario==1)
		{
			System.out.println();
			System.out.print("Informe o valor do pagamento: ");
			auxValorPagamento = sc.nextFloat();
			System.out.print("Informe a próxima data de do pagamento: ");
			auxDataPagamento = sc.next();			
		}
		
		if (tipo==1)
			if (contaSalario==1)
				auxConta = new ContaCorrente(auxValorPagamento, Utilitarios.converterData(auxDataPagamento));
			else
				auxConta = new ContaCorrente();
		else
			if (contaSalario==1)
				auxConta = new ContaPoupanca(auxValorPagamento, Utilitarios.converterData(auxDataPagamento));
			else
				auxConta = new ContaPoupanca();

		
		auxPessoa.contas.add(auxConta);
	}
	
	public static void ListarClientesContas()
	{
		System.out.println();
		System.out.print("Listagem geral de contas");
		for (Pessoa p : clientes)
		{
			System.out.println(p);
			for (Conta c : p.contas)
			{
				System.out.println(c);
			}
			System.out.println();
		}
	}
	
	public static void CaixaEletronico() throws Exception
	{
		int opcao = -1, auxNumero, tipo;
		String auxCPF, auxSenha, auxPix, auxCodigoBarras, auxDataVencimento;
		float auxValor;
		Pessoa pessoaLogada;
		Conta auxConta, auxContaDestino;
	
		
		System.out.print("Informe o CPF: ");
		auxCPF = sc.next();
		System.out.print("Informe a senha: ");
		auxSenha = sc.next();
		
		pessoaLogada = getPessoaPorCPF(auxCPF);
		if (pessoaLogada!=null)
		{
			if (!pessoaLogada.getSenha().equals(auxSenha))
			{
				pessoaLogada=null;
			}
		}
		
		if (pessoaLogada==null)
			throw new Exception("Houve um erro na autenticação! Tente novamente.");
		
		
		
		while(opcao!=0)
		{
		
			try {
				System.out.println();
				System.out.println("Cliente logado: " + pessoaLogada);
				System.out.println();
				System.out.println("O que deseja fazer?");
				System.out.println("(1) Emitir extrato");
				System.out.println("(2) Sacar");
				System.out.println("(3) Depositar");
				System.out.println("(4) Realizar Transferência");
				System.out.println("(5) Pagar boleto");
				System.out.println("(6) Cadastrar chave PIX");
				System.out.println("(0) Voltar");
				
				opcao = sc.nextInt();
				
				switch(opcao)
				{
					case 1: // extrato
						System.out.print("Informe o número da conta: ");
						auxNumero = sc.nextInt();
						
						auxConta = pessoaLogada.getContaPorNumero(auxNumero);
						if (auxConta==null)
							throw new Exception("Conta não encontrada!");
						
						int linha = 1;
						System.out.println();
						System.out.println("Extrato da conta");
						System.out.println();
						System.out.print(auxConta);
						System.out.println(" - Saldo: R$ "+auxConta.getSaldo());
						
						System.out.println();
						for (TransacaoEmConta t : auxConta.transacoes)
						{
							System.out.println("#"+linha+"\t" + Utilitarios.formatarData(t.getData()) + ", "+t.getTipoOperacao());
							linha++;
						}
						System.out.println();
						System.out.println("Informe o número da linha para detalhes ou 0 (zero) para voltar: ");
						tipo = sc.nextInt();
						if(tipo>0 && tipo <= auxConta.transacoes.size())
						{
							System.out.println(auxConta.transacoes.get(tipo-1).getDetalhes());
						}
						else
						{
							throw new Exception("Número da linha de transação é inválido!");
						}
						break;
					
					case 2: // sacar
						System.out.print("Informe o número da conta: ");
						auxNumero = sc.nextInt();
						
						auxConta = pessoaLogada.getContaPorNumero(auxNumero);
						if (auxConta==null)
							throw new Exception("Conta não encontrada!");
						
						System.out.print("Informe o valor: ");
						auxValor = sc.nextFloat();
						
						auxConta.Sacar(auxValor);
						System.out.println("Valor depositado com sucesso!");
						System.out.println();				
						break;
						
					case 3: // depositar
						System.out.print("Informe o número da conta: ");
						auxNumero = sc.nextInt();
						
						auxConta = pessoaLogada.getContaPorNumero(auxNumero);
						if (auxConta==null)
							throw new Exception("Conta não encontrada!");
						
						System.out.print("Informe o valor: ");
						auxValor = sc.nextFloat();
						
						auxConta.Depositar(auxValor);
						System.out.println("Valor depositado com sucesso!");
						System.out.println();	
						break;
						
					case 4: // transferência
						System.out.println();
						System.out.print("Informe o número da conta de oritem: ");
						auxNumero = sc.nextInt();
						
						auxConta = pessoaLogada.getContaPorNumero(auxNumero);
						if (auxConta==null)
							throw new Exception("Conta não encontrada!");
						
						System.out.println();
						System.out.println("Tipo de transferência:");
						System.out.println("(1) Por número da conta");
						System.out.println("(2) Por chave PIX");
						tipo = sc.nextInt();
						switch(tipo)
						{
							case 1:
								System.out.print("Informe o número da conta de destino: ");
								auxNumero = sc.nextInt();
								
								auxContaDestino = getContaPorNumero(auxNumero);
								if (auxContaDestino==null)
									throw new Exception("Conta não encontrada!");
								break;
							case 2:
								System.out.print("Informe a chave PIX desejada: ");
								auxPix = sc.next();
								
								auxContaDestino = getContaPorPIX(auxPix);
								if (auxContaDestino==null)
									throw new Exception("Conta não encontrada!");
								break;
							default:
								throw new Exception("Opção inválida!\n");
						}
						
						System.out.println();
						System.out.print("Informe o valor: ");
						auxValor = sc.nextFloat();
						
						auxConta.Transferir(auxValor, auxContaDestino);
						System.out.println("Transferência realizada com sucesso!");
						System.out.println();	
						
						break;
						
					case 5:
						System.out.print("Informe o número da conta de onde sairá os recursos: ");
						auxNumero = sc.nextInt();
						auxConta = pessoaLogada.getContaPorNumero(auxNumero);
						if (auxConta==null)
							throw new Exception("Conta não encontrada!");
						
						System.out.println("Código de barras: ");
						auxCodigoBarras = sc.next();

						System.out.println("Valor: ");
						auxValor = sc.nextFloat();
						
						System.out.println("Data Vencimento: ");
						auxDataVencimento = sc.next();
						
						auxConta.PagarBoleto(new TransacaoEmContaBoleto(auxCodigoBarras,auxValor,Utilitarios.converterData(auxDataVencimento),Conta.dataAtual));

						System.out.println("Boleto pago com sucesso!");
						System.out.println();	
						
						break;
						
					case 6:
						System.out.print("Informe o número da conta: ");
						auxNumero = sc.nextInt();
						
						auxConta = pessoaLogada.getContaPorNumero(auxNumero);
						if (auxConta==null)
							throw new Exception("Conta não encontrada!");
						
						System.out.println();
						System.out.println("Tipo de chave PIX:");
						System.out.println("(1) CPF");
						System.out.println("(2) Email");
						System.out.println("(3) Telefone");
						System.out.println("(4) Chave nova aleatória");
						tipo = sc.nextInt();
						
						switch(tipo)
						{
							case 1:
								auxConta.setChavePix(pessoaLogada.getCPF());
								break;
							case 2:
								auxConta.setChavePix(pessoaLogada.getEmail());
								break;
							case 3:
								auxConta.setChavePix(pessoaLogada.getTelefone());
								break;
							case 4:
								auxConta.gerarChavePixAleatoria();
								break;
							default:
								throw new Exception("Opção inválida!\n");							
						}
						
						System.out.println("Chave PIX cadastrada com sucesso!");
						System.out.println();	
						break;
						
					case 0:
						// faz nada, pois vai sair
						break;
						
					default:
						throw new Exception("Opção inválida!\n");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println();
			}

		}
	}

	public static void main(String[] args) throws Exception {

		
		int opcao = -1, qtdDias;		

		// ------------------------------------------------------------
		// trecho para facilitar os testes
		clientes.add(new Pessoa("Nome 1", "1", Utilitarios.converterData("29/10/1980"),"email@empresa.com","1199999999","1"));
		clientes.add(new Pessoa("Nome 2", "2", Utilitarios.converterData("29/10/1980"),"email@empresa.com","1199999999","2"));
		
		clientes.get(0).contas.add(new ContaCorrente(1000,Utilitarios.somarDiasData(new Date(),1)));
		clientes.get(0).contas.add(new ContaPoupanca(1000,Utilitarios.somarDiasData(new Date(),1)));
		
		clientes.get(1).contas.add(new ContaCorrente());
		clientes.get(1).contas.add(new ContaPoupanca());
		// ------------------------------------------------------------
		
		while(opcao!=0)
		{
		
			try {
				System.out.println();
				System.out.println("Data atual: " + Utilitarios.formatarData(Conta.dataAtual));
				System.out.println();
				System.out.println("O que deseja fazer?");
				System.out.println("(1) Acessar caixa eletrônico");
				System.out.println("(2) Abrir conta");
				System.out.println("(3) Avançar no tempo");
				System.out.println("(4) Listar todos os clientes e contas");
				System.out.println("(0) Sair");
				
				opcao = sc.nextInt();
				
				switch(opcao)
				{
					case 1:
						CaixaEletronico();
						break;
					
					case 2:
						abrirConta();
						System.out.println("Conta aberta com sucesso!");
						System.out.println();
						
						break;
						
					case 3:
						System.out.print("Informe a quantidade de dias para avançar: ");
						qtdDias = sc.nextInt();
						Conta.AvancarData(qtdDias, clientes);
						break;
						
					case 4:
						ListarClientesContas();
						break;
						
					case 0:
						// faz nada, pois vai sair
						break;
						
					default:
						throw new Exception("Opção inválida!\n");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println();
			}

		}
			

		sc.close();
	}

}
