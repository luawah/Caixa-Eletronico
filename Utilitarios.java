import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utilitarios {
	
	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public static long calcularDiferenteEmDias(Date d1, Date d2)
	{
		long diffInMillies = d1.getTime() - d2.getTime();
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return diff;
	}
	
	public static Date somarDiasData(Date data, int qtdDias)
	{
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.add(Calendar.DATE, qtdDias); 
        return c.getTime();
	}
	

	public static Date somarMesesData(Date data, int qtdMeses)
	{
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.add(Calendar.MONTH, qtdMeses); 
        return c.getTime();
	}
	
	public static String formatarData(Date data)
	{
		if (data==null) return "N/A";
		return df.format(data);
	}
	
	public static Date converterData(String txtData) throws ParseException
	{
		return df.parse(txtData);
	}
	
}
