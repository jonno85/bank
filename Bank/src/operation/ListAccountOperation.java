package operation;

import java.util.Iterator;

import bank.Account;
import bank.Agency;

public class ListAccountOperation extends StrictOperation {

	public ListAccountOperation(Agency agency) {
		super(agency);
	}

	@Override
	public void doOperation(Account ref, Object[] objs)
			throws InvalidArgumentException, InvalidOperationException
	{
		int		num_elem		= 0;
		Iterator<Account> it	= working_agency.getAccounts();
		
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
	}

}
