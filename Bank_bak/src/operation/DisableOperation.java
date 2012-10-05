package operation;

import bank.Account;
import bank.Operator;
import bank.TypeOperator;

public class DisableOperation extends Operation {

	public DisableOperation() {
		super(TypeOperation.CLOSE_ACCOUNT);	
	}

	@Override
	public void doOperation(Account ref, Object[] objs, Operator oper)
			throws InvalidArgumentException, InvalidOperationException,
			InvalidPermissionException
	{
		if((oper.getType().equals(TypeOperator.AGENT)) || 
		   (oper.getType().equals(TypeOperator.ADMINISTRATOR)))
		{
			if(ref.getActiveStatus() == true)
			{
				ref.setActiveStatus(false);
				System.out.println("# Account: " + ref.getAccountHolder() + " disabled");
			} else {
				throw new InvalidOperationException("Error: account already disabled");
			}
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
	}

}
