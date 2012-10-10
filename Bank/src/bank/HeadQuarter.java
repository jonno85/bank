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
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class HeadQuarter extends Observable
{
	public static Timer periodic_operation	= null; //to calculate interest value
	
	public  static final int BANK_PORTFOLIO	= 10;
	private Account bank_account			= null;

	private Map<String,Agency> agencies =
			new HashMap<String,Agency>();

	private static BankConcurrentHashMap treasureStocks =
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
			fin = new FileInputStream(".\\treasureStock.ser");
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
		
		beginTreasureInterestCounter();
    }
    
    /**
     * manage an async thread to advise Financial Items
     */
    private void beginTreasureInterestCounter()
    {
    	periodic_operation = new Timer();
    	periodic_operation.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				_notify();
			}
		}, new Long(5000), new Long(120000));
		

	}
    
    /**
     * collect the useful Financial Items to advise that a new interest operation is needed
     */
    public void _notify()
    {
    	//register Financial Items
    	Iterator<FinancialItem> it = treasureStocks.values().iterator();
    	while (it.hasNext())
    	{
			FinancialItem financialItem = (FinancialItem) it.next();
			Integer life				= financialItem.getLife();
			if(life > 0)
			{
				this.addObserver(financialItem);
				financialItem.setLife(--life);
			}
		}
   	 	this.setChanged();
   	 	this.notifyObservers();
   	 	this.deleteObservers();
    }

	public void storeFinancials()
    {
    	FileOutputStream   fout = null;
		ObjectOutputStream oos  = null;
		try {
			fout = new FileOutputStream(".\\treasureStock.ser");
			oos  = new ObjectOutputStream(fout);

			oos.writeObject(treasureStocks);
			oos.close();
			System.out.println("#Serialization Financial products: Done");

		} catch (FileNotFoundException e) {
			System.err.println("No stored file founded");
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.err.println("#Serialization Financial products: Fail");
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
    				   			 FinancialItemValues.getValue(seed),    //state bond value
    				   			 1 + new Random().nextInt(20), 			//bond life
    				   			 seed + new Random().nextFloat() + 1);  //tax rate
    		treasureStocks.put(new Integer(i),item);
    	}
    }

	protected Agency getNewAgency(String name) throws BankException
	{
		if((!agencies.containsKey(name)) && (name.length() > 1))
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
    
    public FinancialItem getFinancialItemByID(Integer ID) throws BankException
    {
    	FinancialItem fi = null;
    	try{
    		fi = treasureStocks.get(ID);
    	} catch (ClassCastException cce) {
    		throw new BankException("Impossible find the selected Financial Item");
    	} catch (NullPointerException npe) {
    		throw new BankException("Impossible find the selected Financial Item");
    	}
    	return fi;
    }
    
    public static boolean isFinancialItemExist(FinancialItem fi)
    {
    	Iterator<FinancialItem> it = treasureStocks.values().iterator();
    	while (it.hasNext()) {
			 if(it.next().equals(fi))
			 {
				 return true;
			 }
		}
    	return false;
    }

}
