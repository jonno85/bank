package financialItem;

import bank.Account;
import bank.Utils;

public abstract class FinancialItem {

	private Account	owner	 		= null;
	private Integer	account_number	= null;
	
	protected FinancialItem(Account owner)
	{
		this.owner = owner;
	}
	
	/**
	 * @return the owner
	 */
	protected Account getOwner() {
		return owner;
	}
	
	/**
	 * @param owner the owner to set
	 */
	protected void setOwner(Account owner) {
		this.owner = owner;
	}
	
	/**
	 * @return the account_number
	 */
	protected Integer getAccountNumber() {
		return account_number;
	}
	
	/**
	 * @param account_number the account_number to set
	 */
	protected void setAccountNumber(Integer account_number) {
		this.account_number = account_number;
	}
	
	protected abstract void startRateInterest();
	
}
