package operation;

import operation.exception.InvalidArgumentException;
import operation.exception.InvalidOperationException;
import operation.exception.InvalidPermissionException;

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
			if((ref != null) && (ref.getActiveStatus() == true))
			{
				System.out.println(ref.getAccountHolder());
				System.out.println(ref.getAccountBalance());
			} else {
				throw new InvalidOperationException("Account not enabled or not selected");
			}
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
			
	}

}
