package bank;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import financialItem.FinancialItem;
import financialItem.StateBond;
import financialItem.StateBondValues;

public class HeadQuarter
{
	private final int BANK_PORTFOLIO	= 100;
	private Account bank_account		= null;
	
	private Map<String,Agency> agencies = 
			new HashMap<String,Agency>();
	
	private ConcurrentHashMap<Integer,FinancialItem> treasureStocks = 
			new ConcurrentHashMap<Integer, FinancialItem>(BANK_PORTFOLIO);
	
	// Private constructor prevents instantiation from other classes
	private HeadQuarter()
	{
		bank_account = new Account(Utils.generateAccountNumber(),
								   "Bank",
								   new Float(5000000.0));
		//LoadFinancials();
	}

    /**
    * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
    * or the first access to SingletonHolder.INSTANCE, not before.
    */
    private static class SingletonHolder { 
            public static final HeadQuarter INSTANCE = new HeadQuarter();
    }

    public static HeadQuarter getInstance() {
            return SingletonHolder.INSTANCE;
    }
    
    private void LoadFinancials()
    {
    	for(int i=0; i<BANK_PORTFOLIO; i++)
    	{
    		treasureStocks.put(new Integer(i),
    						   new StateBond(bank_account,
    								   StateBondValues.getValue(new Random().nextInt(7)),  //state bond
    								   new Random().nextInt(20), //bond life
    								   new Random().nextFloat())  //tax rate
    						  );
    	}
    }
	
	protected Agency getNewAgency(String name) throws BankException
	{
		if(!agencies.containsKey(name))
		{
			Agency a = new Agency(name);
			agencies.put(name, a);
			return a;
		}
		throw new BankException("Impossible to create an already present Agency");
	}
	
	protected final Account getBankAccount()
	{
		return bank_account;
	}
	
}
