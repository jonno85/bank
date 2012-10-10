package bank;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import financialItem.FinancialItem;
import financialItem.StateBond;
import operation.Operation;

public class Account implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6873036110960725327L;
	
	private Integer	operation_number				= null;
	private Integer	account_number					= null;
	private String	account_holder					= null;
	private Boolean	account_active					= null;
	private Float	account_balance					= null;
	private Map<Integer, Operation> history			= null;
	private Map<Integer, FinancialItem> portfolio	= null;
	
	/**
	 * create new account with no initial amount
	 * @param account_number
	 * @param account_holder
	 */
	public Account(Integer account_number, String account_holder)
	{
		this.operation_number	= new Integer(0);
		this.account_number		= account_number;
		this.account_holder		= account_holder;
		this.account_active		= new Boolean(false); 
		this.account_balance	= new Float(0.0); 
		this.history 			= new HashMap<Integer, Operation>();
	}

	/**
	 * create new account with initial amount
	 * @param account_number
	 * @param account_holder
	 * @param initial_balance
	 */
	public Account(Integer account_number, String account_holder, Float initial_balance, Boolean active)
	{
		this.operation_number	= new Integer(0);
		this.account_number		= account_number;
		this.account_holder		= account_holder;
		this.account_active		= active;
		this.account_balance	= initial_balance; 
		this.history 			= new HashMap<Integer, Operation>();
	}
	
	/**
	 * return active account status
	 * @return
	 */
	public boolean getActiveStatus()
	{
		return this.account_active;
	}
	
	/**
	 * set active account status
	 * @return
	 */
	public void setActiveStatus(boolean status)
	{
		this.account_active = status;
	}
	
	/**
	 * resolve the operation by call the overloaded method "doOperation",
	 * update history account
	 * @param op
	 * @param obj
	 *
	public Boolean resolveOperation(Operation op, Object[] objs, Operator oper)
	{
		if(op != null)
		{
			switch(op.getType())
			{
			case WITHDRAWAL:
			case DEPOSIT:
				op.doOperation(this, objs, oper);
				break;
			case OPEN_ACCOUNT:
			case CLOSE_ACCOUNT:
				op.doOperation(this, null, oper);
				break;
			case BUY_FINANCIAL_ITEM:
			case SELL_FINANCIAL_ITEM:
				op.doOperation(this, objs, oper);
				break;
			}
			history.put(operation_number++, op);
		} else {
			return false;
		}
		return true;
	}

	*/

	public Integer getAccountNumber() {
		return account_number;
	}

	/**
	 * @return the account_balance
	 */
	public Float getAccountBalance() {
		return account_balance;
	}

	/**
	 * @param account_balance the account_balance to set
	 */
	public void setAccountBalance(Float account_balance) {
		this.account_balance = account_balance;
	}
	
	/**
	 * @return the account_holder
	 */
	public String getAccountHolder() {
		return account_holder;
	}

	/**
	 * as not all the clients do financial transaction,
	 * this relative structure is required to be inizialized a part
	 */
	public void inizializePortfolio()
	{
		portfolio = new HashMap<Integer, FinancialItem>();
	}
	
}
