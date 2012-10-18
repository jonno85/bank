package operation;

import operation.exception.InvalidArgumentException;
import operation.exception.InvalidOperationException;
import operation.exception.InvalidPermissionException;
import operation.exception.OperationException;
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
					
					try{ //able to withdrawal from the source account
						new WithdrawalOperation().doOperation(ref, arg, oper);
						
						try{ //able to deposit to the destination account
							Account dest = (Account)objs[0];
							new DepositOperation().doOperation(dest, arg, oper);
						} catch(InvalidOperationException op) {
							new DepositOperation().doOperation(ref, arg, oper);
							throw new InvalidOperationException("Account not enabled or not selected");
						}
					} catch(InvalidOperationException op) {
						throw new InvalidOperationException("Error: impossible to withdrawal money");
					}
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
