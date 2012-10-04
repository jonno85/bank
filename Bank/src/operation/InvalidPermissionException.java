package operation;

public class InvalidPermissionException extends OperationException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String exception_message = null;

	public InvalidPermissionException() {
		super(); 
		this.exception_message = "none";
	}
	
	public InvalidPermissionException(String message) {
		super(); 
		this.exception_message = message;
	}
	
	public String getMessage()
	{
		return this.exception_message;
	}
}
