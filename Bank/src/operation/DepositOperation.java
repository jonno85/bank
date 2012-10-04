package operation;

import bank.Account;

public class DepositOperation extends Operation {

	public DepositOperation() {
		super();	
		this.type = TypeOperation.DEPOSIT;
	}

	@Override
	public void doOperation(Account ref, Object[] obj)
			throws InvalidArgumentException, InvalidOperationException
	{
		if(ref.getActiveStatus() == true)
		{
			if(obj instanceof String[])
			{
				Float amount = Float.valueOf(obj[0].toString());
				if(amount > 0)
				{
					ref.setAccountBalance(ref.getAccountBalance() + amount);
				} else {
					throw new InvalidOperationException("Impossible deposit a negative value");
				}
			} else {
				throw new InvalidArgumentException("Error: wrong numeric format");
			}
		} else {
			throw new InvalidOperationException("Error: Account closed");
		}
	}

}
