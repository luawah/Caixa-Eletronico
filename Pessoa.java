import java.util.ArrayList;
import java.util.Date;

public class Pessoa {
	
	private String nome;
	private String CPF;
	private Date dataNascimento;
	private String email;
	private String telefone;
	private String senha;
	public ArrayList<Conta> contas = new ArrayList<Conta>();
	
	public Conta getContaPorNumero(int numero)
	{
		for (Conta c : this.contas)
		{
			if (c.getNumeroConta()==numero)
				return c;
		}
		return null;
	}
	

	
	public Pessoa(String nome, String cPF, Date dataNascimento, String email, String telefone, String senha) {
		super();
		this.nome = nome;
		CPF = cPF;
		this.dataNascimento = dataNascimento;
		this.email = email;
		this.telefone = telefone;
		this.senha = senha;
	}
	
	@Override
    public String toString() {
        return "CPF: " + this.CPF + " ("+this.nome + ") Nascimento: "+Utilitarios.formatarData(this.dataNascimento);
    }
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCPF() {
		return CPF;
	}
	public void setCPF(String cPF) {
		CPF = cPF;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

}
