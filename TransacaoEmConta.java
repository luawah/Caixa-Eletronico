import java.util.Date;

public interface TransacaoEmConta {

	float getValor();
	String getDescricao();
	String getTipoOperacao();
	Date getData();
	
	String getDetalhes();
}
