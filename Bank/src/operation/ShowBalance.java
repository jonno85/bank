package operation;

import java.util.Iterator;

import bank.Account;
import bank.Operator;
import bank.TypeOperator;

public class ShowBalance extends Operation
{

	public ShowBalance()
	{
		super(TypeOperation.SHOW_BALANCE);
	}
	
	@Override
	public void doOperation(Account ref, Object[] objs, Operator oper)
			throws InvalidArgumentException, InvalidOperationException,
			InvalidPermissionException
	{
		if((oper.getType().equals(TypeOperator.AGENT)) 	|| 
		   (oper.getType().equals(TypeOperator.CLIENT))	||
		   (oper.getType().equals(TypeOperator.ADMINISTRATOR)))
		{
			System.out.println(ref.getAccountHolder());
			System.out.println(ref.getAccountBalance());
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
			
	}

}
