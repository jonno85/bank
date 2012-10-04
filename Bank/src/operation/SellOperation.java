package operation;

import bank.Account;
import bank.Operator;
import bank.TypeOperator;

public class SellOperation extends Operation {

	public SellOperation() {
		super();	
		this.type = TypeOperation.SELL_FINANCIAL_ITEM;
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
				//TODO return the bond to the bank 
			} else {
				throw new InvalidOperationException("Impossible to buy bond due to disabled account or not selected");
			}
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
	}
}
