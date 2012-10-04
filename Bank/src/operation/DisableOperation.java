package operation;

import bank.Account;

public class DisableOperation extends Operation {

	public DisableOperation() {
		super();	
		this.type = TypeOperation.CLOSE_ACCOUNT;
	}

	@Override
	public void doOperation(Account ref, Object[] objs)
			throws InvalidArgumentException, InvalidOperationException
	{
		if(ref.getActiveStatus() == true)
		{
			ref.setActiveStatus(false);
			System.out.println("# Account: " + ref.getAccountHolder() + " disabled");
		} else {
			throw new InvalidOperationException("Error: account already disabled");
		}
		
	}

}
