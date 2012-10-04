package operation;

import bank.Account;

public class WithdrawalOperation extends Operation {

	public WithdrawalOperation() {
		super();	
		this.type = TypeOperation.WITHDRAWAL;
	}

	@Override
	public void doOperation(Account ref, Object[] objs)
			throws InvalidArgumentException, InvalidOperationException 
	{
		if(ref.getActiveStatus() == true)
		{
			if(objs instanceof String[])
			{
				Float amount = Float.valueOf(objs[0].toString());
				amount = ref.getAccountBalance() - amount;
				if(amount >= 0.0)
				{
					ref.setAccountBalance(amount);
				} else {
					throw new InvalidOperationException("Insufficient credit balance");
				}
			} else {
				throw new InvalidArgumentException("Error: wrong numeric format");
			}
		} else {
			throw new InvalidOperationException("Account not opened");
		}
		
	}

	
}
