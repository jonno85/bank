package bank;


import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import operation.InvalidArgumentException;
import operation.InvalidOperationException;
import operation.Operation;

public class Operator {
	
	private String		 working_name	 = null;
	private Agency		 working_agency  = null;
	private Account		 bank_account	 = null;
	private Account		 working_account = null;
	private Operation	 working_oper	 = null;
	private Integer		 counter		 = null;
	private TypeOperator type			 = null;
	private Map<Integer,Operation> history = null;
	
	/**
	 * full constructor
	 * @param name
	 * @param agency
	 * @param bank_account
	 * @param account
	 * @param operation
	 */
	public Operator(String name, Agency agency, Account bank_account, 
			Account account, Operation operation, TypeOperator type)
	{
		this.working_name		= name;
		this.working_agency		= agency;
		this.bank_account		= bank_account;
		this.working_account	= account;
		this.working_oper		= operation;
		this.history 			= new HashMap<Integer, Operation>();
		this.counter			= new Integer(0);
		this.type				= type;
		
		addHistoryOperation();
	}
	
	/**
	 * base constructor
	 * @param name
	 * @param agency
	 * @param bank_account
	 * @param account
	 */
	public Operator(String name, Agency agency, Account bank_account,
			Account account, TypeOperator type)
	{
		this.working_name		= name;
		this.working_agency		= agency;
		this.bank_account		= bank_account;
		this.working_account	= account;
		this.history 			= new HashMap<Integer, Operation>();
		this.counter			= new Integer(0);
		this.type				= type;
	}
	
	public TypeOperator getType()
	{
		return type;
	}
	
	/**
	 * save information about last operation executed
	 */
	private void addHistoryOperation()
	{
		if(working_oper != null)
			history.put(counter++, this.working_oper);
	}
	
	/**
	 * change the operation to execute
	 * @param op
	 */
	public void setOperation(Operation op)
	{
		this.working_oper = op;
	}
	
	/**
	 * change the current working account
	 * @param current_account
	 */
	public void setWorkingAccount(Account current_account)
	{
		this.working_account = current_account;
	}
	
	/**
	 * execute the actual operation
	 * @param parameter: specific for the kind of operation to execute
	 * @throws InvalidOperationException
	 */
	public void execOperation(Object[] parameters) 
			throws InvalidArgumentException, InvalidOperationException
	{
		if(working_account != null)
		{
			System.out.println("# Account Selected: " + this.working_account.getAccountNumber() + " - " + this.working_account.getAccountHolder());
		}
		
		if(working_oper != null)
		{
			// !!working_account could not be necessary!! like for NewAccountOperation
			working_oper.doOperation(working_account, parameters, this);
			addHistoryOperation();
			
			/*** clean operation data ***/ 
			working_oper	= null;
			parameters		= null;
		} else {
			throw new InvalidOperationException("Error: no operation defined");
		}
	}
	
	public Iterator<Operation> getHistoryOperator()
	{
		return history.values().iterator();
	}

}
