package operation;

import java.util.Date;
import java.util.GregorianCalendar;

import bank.Account;
import bank.Operator;

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
	
	public abstract void doOperation(Account ref, Object[] objs, Operator oper)
			throws InvalidArgumentException, InvalidOperationException, InvalidPermissionException;

	public String getInfo() {
		return dateTime.toString() + " " + type.toString();
	}
	
}
