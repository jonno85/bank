package operation;


import financialItem.StateBond;

import bank.Account;
import bank.Operator;
import bank.TypeOperator;

public class BuyOperation extends Operation {

	public BuyOperation() {
		super(TypeOperation.BUY_FINANCIAL_ITEM);
	}

			
	@Override
	public void doOperation(Account ref, Object[] objs, Operator oper)
			throws InvalidArgumentException, InvalidOperationException,
			InvalidPermissionException
	{
		if((oper.getType().equals(TypeOperator.AGENT)) 		   || 
		   (oper.getType().equals(TypeOperator.ADMINISTRATOR)))
		{	
			if((ref != null) && (ref.getActiveStatus() == true))
			{
				StateBond bond = (StateBond)objs[0];
				if(ref.getAccountBalance() >= bond.getStateBondValue().getIntegerValue())
				{
					
				} else {
					throw new InvalidOperationException("Impossible to buy bond due to insufficient balance");
				}
			} else {
				throw new InvalidOperationException("Impossible to buy bond due to disabled account or not selected");
			}
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}	
	}
}
