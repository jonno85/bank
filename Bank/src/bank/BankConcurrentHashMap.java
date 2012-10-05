package bank;

import java.io.Serializable;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import financialItem.FinancialItem;

public class BankConcurrentHashMap extends TreeMap<Integer, FinancialItem> 
								   implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8961829677693743536L;

	public BankConcurrentHashMap(int initial_values)
	{
		super();
	}

}
