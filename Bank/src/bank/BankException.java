package bank;

public class BankException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String exception_message = null;

	public BankException() {
		super(); 
		this.exception_message = "none";
	}
	
	public BankException(String message) {
		super(); 
		this.exception_message = message;
	}
	
	public String getMessage()
	{
		return this.exception_message;
	}
}
