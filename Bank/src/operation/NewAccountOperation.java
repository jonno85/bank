package operation;

import operation.exception.InvalidArgumentException;
import operation.exception.InvalidOperationException;
import operation.exception.InvalidPermissionException;
import bank.Account;
import bank.Agency;
import bank.Operator;
import bank.TypeOperator;

public class NewAccountOperation extends StrictOperation
{
	
	public NewAccountOperation(Agency agency) {
		super(agency, TypeOperation.NEW_ACCOUNT);	
	}

	/**
	 * in this case the account ref is the bank account but not useful
	 */
	@Override
	public void doOperation(Account ref, Object[] objs, Operator oper)
			throws InvalidArgumentException, InvalidOperationException,
			InvalidPermissionException
	{
		if((oper.getType().equals(TypeOperator.AGENT)) || 
		   (oper.getType().equals(TypeOperator.ADMINISTRATOR)))
		{
			if(objs instanceof String[])
			{
				String name = objs[0].toString();
				if(working_agency.accountExist(name) == false)
				{
					working_agency.addAccount(name);
					System.out.println("# Account " + name + " created");
				} else {
					throw new InvalidArgumentException("Error: Account name already exist");
				}
			} else {
				throw new InvalidArgumentException("Error: Not valid name");
			}
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
	}

}
