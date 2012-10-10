package bank;


import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import operation.Operation;
import operation.exception.InvalidArgumentException;
import operation.exception.InvalidOperationException;

public class Operator {
	
	private String		 working_name	   = null;
	private Agency		 working_agency    = null;
	private Account		 bank_account	   = null;
	private Account		 working_account   = null;
	private Operation	 working_oper	   = null;
	private Integer		 counter		   = null;
	private TypeOperator type			   = null;
	private Map<Integer,String> history    = null;
	
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
		this.working_name		= checkOperatorName(name);
		this.working_agency		= agency;
		this.bank_account		= bank_account;
		this.working_account	= account;
		this.working_oper		= operation;
		this.history 			= new HashMap<Integer, String>();
		this.counter			= new Integer(0);
		this.type				= type;
		
		addHistoryOperation(null);
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
		this.working_name		= checkOperatorName(name);
		this.working_agency		= agency;
		this.bank_account		= bank_account;
		this.working_account	= account;
		this.history 			= new HashMap<Integer, String>();
		this.counter			= new Integer(0);
		this.type				= type;
	}
	
	/**
	 * check the correctness of the passed name string
	 * @param holder
	 * @return
	 */
	private String checkOperatorName(String op_name)
	{
		String name = op_name;
		if(op_name == null)
			name = "unknown name";
		if(op_name.length() == 0)
			name = "unknown name";
		return name;
	}
	
	public TypeOperator getType()
	{
		return type;
	}
	
	/**
	 * save information about last operation executed
	 */
	private void addHistoryOperation(Object[] parameters)
	{
		String s =  " "                  + (working_oper != null? working_oper.getInfo() : "no user ");		
			   s += "\t\t # name: "      + (working_name != null? working_name : "no name "); 
		       s += "\n\t # worked on: " + (working_account != null? working_account.getAccountHolder() : "no account ");

        // pay attention to which case use different objects values
		if(parameters != null)
		{
			if((parameters instanceof String[]) && (parameters[0] != ""))
				s += "\t # parameters:" + (String)parameters[0];
		}
		history.put(counter++, s);
	}
	
	/**
	 * change the operation to execute
	 * @param op
	 */
	public void setOperation(Operation op)
	{
		this.working_oper = op;
	}
	
	public Operation getOperation()
	{
		return this.working_oper;
	}
	
	/**
	 * change the current working account
	 * @param current_account
	 */
	public boolean setWorkingAccount(Account current_account)
	{
		if(current_account != null)
		{
			working_account = current_account;
			return true;
		}
		return false;
	}
	
	public Account getWorkingAccount()
	{
		return working_account;
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
			addHistoryOperation(parameters);
			
			/*** clean operation data ***/ 
//			working_oper	= null;
//			parameters		= null;
		} else {
			throw new InvalidOperationException("Error: no operation defined");
		}
	}
	
	public Iterator<String> getHistoryOperator()
	{
		return history.values().iterator();
	}
	
	public Account getBankAccount()
	{
		if((type != TypeOperator.CLIENT) || 
		   (type != TypeOperator.ATM))
		{
			return bank_account;
		}
		return null;
	}

}
