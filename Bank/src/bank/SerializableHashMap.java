package bank;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;


public class SerializableHashMap extends HashMap<Integer, Account> implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Integer base = new Integer(10000);
	
	
	
	public Integer generateAccountNumber()
	{
		return ++base;
	}
	
	/**
	 * load, if exist, the last account id generated
	 */
	public void loadStoredValue()
	{
		base += this.size();
	}

	/**
	 * retrieve key value from the name parameter
	 * @param name
	 * @return
	 */
	public Integer getKeyByNameAccount(String name)
	{
		Account account		 = null;
		Iterator<Account> it = this.values().iterator();
		while (it.hasNext())
		{
			account = it.next();
			if(account.getAccountHolder().equalsIgnoreCase(name) == true)
				return account.getAccountNumber();
		}
		return new Integer(-1);
	}
	
}
