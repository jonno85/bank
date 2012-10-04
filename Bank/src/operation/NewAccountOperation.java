package operation;

import bank.Account;
import bank.Agency;

public class NewAccountOperation extends StrictOperation
{
	
	public NewAccountOperation(Agency agency) {
		super(agency);	
	}

	/**
	 * in this case the account ref is the bank account but not useful
	 */
	@Override
	public void doOperation(Account ref, Object[] objs)
			throws InvalidArgumentException, InvalidOperationException
	{
		if(objs instanceof String[])
		{
			String name = objs[0].toString();
			if(working_agency.accountExist(name) == false)
			{
				working_agency.addAccount(name);
			} else {
				throw new InvalidArgumentException("Account name already exist");
			}
		} else {
			throw new InvalidArgumentException("Error: Not valid name");
		}
	}

}
