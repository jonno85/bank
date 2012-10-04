package bank;


import java.util.HashMap;
import java.util.Map;

import operation.InvalidArgumentException;
import operation.InvalidOperationException;
import operation.Operation;

public class Operator {
	
	private String		working_name	= null;
	private Agency		working_agency	= null;
	private Account		working_account	= null;
	private Operation	working_oper	= null;
	private Integer		counter			= null;
	private Map<Integer,Operation> history = null;
	
	/**
	 * full constructor
	 * @param agency
	 * @param account
	 * @param operation
	 */
	public Operator(String name, Agency agency, Account account, Operation operation)
	{
		this.working_name		= name;
		this.working_agency		= agency;
		this.working_account	= account;
		this.working_oper		= operation;
		this.history 			= new HashMap<Integer, Operation>();
		this.counter			= new Integer(0);
		
		addHistoryOperation();
	}
	
	/**
	 * base constructor
	 * @param agency
	 * @param account
	 */
	public Operator(String name, Agency agency, Account account)
	{
		this.working_name		= name;
		this.working_agency		= agency;
		this.working_account	= account;
		this.history 			= new HashMap<Integer, Operation>();
		this.counter			= new Integer(0);
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
	 * execute the actual operation
	 * @param parameter: specific for the kind of operation to execute
	 * @throws InvalidOperationException
	 */
	public void execOperation(Object[] parameters) 
			throws InvalidArgumentException, InvalidOperationException
	{
		if(working_oper != null)
		{
			
			//System.out.print("operazione: "+ working_oper.toString() + " parameters: "+ parameters.toString()+" lemeneti: "+parameters.toString());
			working_oper.doOperation(working_account, parameters);
			addHistoryOperation();
			
			/*** clean operation data ***/
			working_oper	= null;
			parameters		= null;
			
		} else {
			throw new InvalidOperationException("Error: no operation defined");
		}
	}

}
