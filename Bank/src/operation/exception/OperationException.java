package operation.exception;

public class OperationException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String exception_message = null;
	
	public OperationException(){
		
	}
	
	public OperationException(String message) {
		super(); 
		this.exception_message = message;
	}
	
	public String getMessage()
	{
		return this.exception_message;
	}
}
