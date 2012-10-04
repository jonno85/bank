package operation;

import bank.Account;

public class SellOperation extends Operation {

	public SellOperation() {
		super();	
		this.type = TypeOperation.SELL_FINANCIAL_ITEM;
	}

	@Override
	public void doOperation(Account ref, Object[] obj)
		throws InvalidOperationException
	{
		if(ref.getActiveStatus() == true)
		{
			//TODO return the bond to the bank 
		} else {
			throw new InvalidOperationException("Impossible to buy bond due to closed account");
		}
	}


}
