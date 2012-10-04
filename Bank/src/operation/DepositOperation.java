package operation;

import bank.Account;
import bank.Operator;
import bank.TypeOperator;

public class DepositOperation extends Operation {

	public DepositOperation() {
		super(TypeOperation.DEPOSIT);	
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
					Float amount = null;
					try{
						amount = Float.valueOf(objs[0].toString());
						if(amount > 0.0)
						{
							System.out.println("# Account: " + ref.getAccountHolder() + " deposit: "+ amount);
							ref.setAccountBalance(ref.getAccountBalance() + amount);
						} else {
							throw new InvalidOperationException("Impossible deposit a negative value");
						}
					} catch (NumberFormatException nfe){
						throw new InvalidArgumentException("Error: wrong numeric format");
					}
					
				} else {
					throw new InvalidArgumentException("Error: wrong numeric format");
				}
			} else {
				throw new InvalidOperationException("Error: Account disabled or not selected");
			}
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
	}

}
