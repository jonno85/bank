package operation;

public class InvalidArgumentException extends InvalidOperationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String exception_message = null;

	public InvalidArgumentException() {
		super(); 
		this.exception_message = "none";
	}
	
	public InvalidArgumentException(String message) {
		super(); 
		this.exception_message = message;
	}
	
	public String getMessage()
	{
		return this.exception_message;
	}

}
