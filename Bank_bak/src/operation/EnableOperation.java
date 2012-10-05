package operation;

import bank.Account;
import bank.Operator;
import bank.TypeOperator;

public class EnableOperation extends Operation
{

	public EnableOperation() {
		super(TypeOperation.OPEN_ACCOUNT);	
	}

	@Override
	public void doOperation(Account ref, Object[] objs, Operator oper)
			throws InvalidArgumentException, InvalidOperationException,
			InvalidPermissionException
	{
		if((oper.getType().equals(TypeOperator.AGENT)) || 
		   (oper.getType().equals(TypeOperator.ADMINISTRATOR)))
	    {
			if((ref != null) && (ref.getActiveStatus() == false))
			{
				ref.setActiveStatus(true);
				System.out.println("# Account: " + ref.getAccountHolder() + " enabled");
			} else {
				throw new InvalidOperationException("Error: account already enabled or not selected");
			}
	    } else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
	}

}
