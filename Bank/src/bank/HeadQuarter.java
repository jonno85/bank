package bank;

import financialItem.FinancialItem;
import financialItem.StateBond;
import financialItem.FinancialItemValues;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

public class HeadQuarter
{
	public static Timer periodic_operation	= null; //to calculate interest value
	
	public  static final int BANK_PORTFOLIO	= 10;
	private Account bank_account			= null;

	private Map<String,Agency> agencies =
			new HashMap<String,Agency>();

	private BankConcurrentHashMap treasureStocks =
			new BankConcurrentHashMap(BANK_PORTFOLIO);

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
    	FileInputStream	  fin = null;
		ObjectInputStream ois = null;
		try {
			fin = new FileInputStream("\\treasureStock.ser");
			ois = new ObjectInputStream(fin);

			treasureStocks = (BankConcurrentHashMap) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			System.err.println("No stored treasureStock data founded");
			createFinancials();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		}
		
		
    	
    }
    
    public void storeFinancials()
    {
    	FileOutputStream   fout = null;
		ObjectOutputStream oos  = null;
		try {
			fout = new FileOutputStream("\\treasureStock.ser");
			oos  = new ObjectOutputStream(fout);

			oos.writeObject(treasureStocks);
			oos.close();
			System.out.println("#Serialization Financial products: Done");

		} catch (FileNotFoundException e) {
			System.err.println("No stored file founded");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
    }
    
    private void createFinancials()
    {
    	FinancialItem item = null;
    	int 		  seed  = 0;;
    	for(int i=0; i<BANK_PORTFOLIO; i++)
    	{
    		seed = new Random().nextInt(6) + 1;
    		item = new StateBond(new Integer(i),
    				   			 bank_account,
    				   			 FinancialItemValues.getValue(seed),  //state bond value
    				   			 new Random().nextInt(20), 			//bond life
    				   			 seed + new Random().nextFloat());  	//tax rate
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
    
    public FinancialItem getFinancialItemByID(Integer ID)
    {
    	
    	return treasureStocks.get(ID);
    }

}
