package operation;

import java.util.Iterator;

import operation.exception.InvalidArgumentException;
import operation.exception.InvalidOperationException;
import operation.exception.InvalidPermissionException;

import bank.Account;
import bank.Operator;
import bank.TypeOperator;

public class ShowHistoryOperation extends Operation {
	
	public ShowHistoryOperation()
	{
		super(TypeOperation.SHOW_HISTORY);
	}

	@Override
	public void doOperation(Account ref, Object[] objs, Operator oper)
			throws InvalidArgumentException, InvalidOperationException,
			InvalidPermissionException
	{
		if((oper.getType().equals(TypeOperator.AGENT)) || 
		   (oper.getType().equals(TypeOperator.ADMINISTRATOR)))
		{
			Iterator<String> it = oper.getHistoryOperator();
			String   	     op = null;
			System.out.println("______________________________________________________________________________________________");
			while (it.hasNext())
			{
				op = it.next();
				System.out.println("## " + op);
			}
			System.out.println("______________________________________________________________________________________________");
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
	}

}
