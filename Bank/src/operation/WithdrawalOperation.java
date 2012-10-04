package operation;

import bank.Account;
import bank.Operator;
import bank.TypeOperator;

public class WithdrawalOperation extends Operation {

	public WithdrawalOperation() {
		super();	
		this.type = TypeOperation.WITHDRAWAL;
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
				if(objs instanceof String[])
				{
					Float amount = Float.valueOf(objs[0].toString());
					amount = ref.getAccountBalance() - amount;
					if(amount >= 0.0)
					{
						System.out.println("# Account: " + ref.getAccountHolder() + " withdrawal: "+ amount);
						ref.setAccountBalance(amount);
					} else {
						throw new InvalidOperationException("Insufficient credit balance");
					}
				} else {
					throw new InvalidArgumentException("Error: wrong numeric format");
				}
			} else {
				throw new InvalidOperationException("Account not enabled or not selected");
			}
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
		
	}

	
}
