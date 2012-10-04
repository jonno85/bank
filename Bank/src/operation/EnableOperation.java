package operation;

import bank.Account;

public class EnableOperation extends Operation
{

	public EnableOperation() {
		super();	
		this.type = TypeOperation.OPEN_ACCOUNT;
	}

	@Override
	public void doOperation(Account ref, Object[] objs)
			throws InvalidArgumentException, InvalidOperationException
	{
		if((ref != null) && (ref.getActiveStatus() == false))
		{
			ref.setActiveStatus(true);
			System.out.println("# Account: " + ref.getAccountHolder() + " enabled");
		} else {
			throw new InvalidOperationException("Error: account already enabled or not selected");
		}
	}

}
