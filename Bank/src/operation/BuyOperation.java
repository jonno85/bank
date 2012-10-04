package operation;


import financialItem.StateBond;

import bank.Account;

public class BuyOperation extends Operation {

	public BuyOperation() {
		super();	
		this.type = TypeOperation.BUY_FINANCIAL_ITEM;
	}

	@Override
	public void doOperation(Account ref, Object[] obj) 
		throws InvalidOperationException
	{
		if(ref.getActiveStatus() == true)
		{
			StateBond bond = (StateBond)obj[0];
			if(ref.getAccountBalance() >= bond.getStateBondValue().getIntegerValue())
			{
				
			} else {
				throw new InvalidOperationException("Impossible to buy bond due to insufficient balance");
			}
		} else {
			throw new InvalidOperationException("Impossible to buy bond due to closed account");
		}
	}

}
