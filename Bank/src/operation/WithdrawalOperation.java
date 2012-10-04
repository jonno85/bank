package operation;

import bank.Account;
import bank.Operator;
import bank.TypeOperator;

public class WithdrawalOperation extends Operation {

	public WithdrawalOperation() {
		super(TypeOperation.WITHDRAWAL);
	}

	@Override
	public void doOperation(Account ref, Object[] objs, Operator oper)
			throws InvalidArgumentException, InvalidOperationException,
			InvalidPermissionException
	{
		if((!oper.getType().equals(TypeOperator.AGENT)) 		&& 
		   (!oper.getType().equals(TypeOperator.ADMINISTRATOR)) &&
		   (!oper.getType().equals(TypeOperator.CLIENT)))
		{
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
		
		if((ref == null) && (ref.getActiveStatus() == false))
		{
			throw new InvalidOperationException("Account not enabled or not selected");
		}
		
		if(!(objs instanceof String[]))
		{
			throw new InvalidArgumentException("Error: wrong numeric format");
		}
		
		Float amount = null;
		try{
			amount = Float.valueOf(objs[0].toString());
			amount = ref.getAccountBalance() - amount;
			if(amount >= 0.0)
			{
				System.out.println("# Account: " + ref.getAccountHolder() + " withdrawal: "+ amount);
				ref.setAccountBalance(amount);
			} else {
				throw new InvalidOperationException("Error: Not enough money on the account\nImpossible withdrawal a negative value");
			}
		} catch (NumberFormatException nfe){
			throw new InvalidArgumentException("Error: wrong numeric format");
		}
		
	}

	
}
