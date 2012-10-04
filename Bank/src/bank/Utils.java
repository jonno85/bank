package bank;

public class Utils
{
	
	private static Integer base = new Integer(10000);
	
	public static Integer generateAccountNumber()
	{
		return base++;
	}
}
