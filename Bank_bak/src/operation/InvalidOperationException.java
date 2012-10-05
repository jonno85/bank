package operation;

public class InvalidOperationException extends OperationException
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String exception_message = null;

	public InvalidOperationException() {
		super(); 
		this.exception_message = "none";
	}
	
	public InvalidOperationException(String message) {
		super(); 
		this.exception_message = message;
	}
	
	public String getMessage()
	{
		return this.exception_message;
	}
}
