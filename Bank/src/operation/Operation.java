package operation;

import java.util.Date;
import java.util.GregorianCalendar;

import bank.Account;

public abstract class Operation
{	
	private		Date			dateTime	= null;
	protected	TypeOperation	type		= null;
	
	public TypeOperation getType()
	{
		return type;
	}
	
	public Operation()
	{
		this.dateTime	= GregorianCalendar.getInstance().getTime();
	}
	
	public Operation(Date dateTime, TypeOperation type)
	{
		this.dateTime	= dateTime;
		this.type		= type;
	}
	
	public abstract void doOperation(Account ref, Object[] objs)
			throws InvalidArgumentException, InvalidOperationException;
	
}
