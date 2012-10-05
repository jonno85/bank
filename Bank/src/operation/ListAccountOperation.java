package operation;

import java.util.Iterator;

import operation.exception.InvalidArgumentException;
import operation.exception.InvalidOperationException;
import operation.exception.InvalidPermissionException;

import bank.Account;
import bank.Agency;
import bank.Operator;
import bank.TypeOperator;

public class ListAccountOperation extends StrictOperation {

	public ListAccountOperation(Agency agency) {
		super(agency, TypeOperation.LIST_ACCOUNTS);
	}

	@Override
	public void doOperation(Account ref, Object[] objs, Operator oper)
			throws InvalidArgumentException, InvalidOperationException,
			InvalidPermissionException
	{
		int	num_elem = 0;
		Iterator<Account> it = null;
		
		if((oper.getType().equals(TypeOperator.AGENT)) || 
		   (oper.getType().equals(TypeOperator.ADMINISTRATOR)))
		{
			it = working_agency.getAccounts();
			
			System.out.println("## Account Number \t| Account Name \t| Status \t| Total amount");
	
			while (it.hasNext()) {
				Account account = it.next();
				String name = account.getAccountHolder().length()>5?account.getAccountHolder():account.getAccountHolder()+"\t";
				System.out.println("#> "+account.getAccountNumber() + "\t\t| " + 
									     name + "\t| "   +
									     account.getActiveStatus()  + "\t\t| "   +
									     account.getAccountBalance());
				num_elem++;
			}
			System.out.println("\n## Number of accounts: " + num_elem);
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
	}

}
