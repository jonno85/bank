package bank;

import financialItem.FinancialItem;
import financialItem.StateBond;
import financialItem.FinancialItemValues;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class HeadQuarter
{
	private final int BANK_PORTFOLIO	= 10;
	private Account bank_account		= null;

	private Map<String,Agency> agencies =
			new HashMap<String,Agency>();

	private ConcurrentHashMap<Integer,FinancialItem> treasureStocks =
			new ConcurrentHashMap<Integer, FinancialItem>(BANK_PORTFOLIO);

	// Private constructor prevents instantiation from other classes
	private HeadQuarter()
	{
		bank_account = new Account(10000,
								   "Bank",
								   new Float(5000000.0),
								   new Boolean(true));
		LoadFinancials();
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
    	FinancialItem item = null;
    	for(int i=0; i<BANK_PORTFOLIO; i++)
    	{
    		item = new StateBond(bank_account,
					   FinancialItemValues.getValue(new Random().nextInt(6) + 1),  //state bond
					   new Random().nextInt(20), //bond life
					   new Random().nextFloat());  //tax rate
    		treasureStocks.put(new Integer(i),item);
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

    public Iterator<FinancialItem> getFinancialIterator()
    {
        return treasureStocks.values().iterator();
    }

}
