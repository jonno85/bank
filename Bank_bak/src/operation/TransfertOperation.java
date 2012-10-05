package operation;

import bank.Account;
import bank.Operator;
import bank.TypeOperator;

public class TransfertOperation extends Operation
{

	public TransfertOperation()
	{
		super(TypeOperation.TRANSFERT_OPERATION);
	}
	
	@Override
	public void doOperation(Account ref, Object[] objs, Operator oper)
			throws InvalidArgumentException, InvalidOperationException,
			InvalidPermissionException
	{
		if((oper.getType().equals(TypeOperator.AGENT)) 		   || 
		   (oper.getType().equals(TypeOperator.ADMINISTRATOR)) ||
		   (oper.getType().equals(TypeOperator.CLIENT)))
		{
			if((ref != null) && (ref.getActiveStatus() == true))
			{
				if(objs instanceof Object[])
				{
					Object[] arg = new String[2];
					arg[0] = objs[1];
					new WithdrawalOperation().doOperation(ref, arg, oper);
					
					Account dest = (Account)objs[0];
					new DepositOperation().doOperation(dest, arg, oper);
					
				} else {
					throw new InvalidArgumentException("Error: wrong numeric or naming format");
				}
			} else {
				throw new InvalidOperationException("Account not enabled or not selected");
			}
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
						
	}

}
