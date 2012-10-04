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
		if(ref.getActiveStatus() == false)
		{
			ref.setActiveStatus(true);
		} else {
			throw new InvalidOperationException("Error: account already enabled");
		}
	}

}
