package bank;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import operation.*;
import operation.exception.InvalidArgumentException;
import operation.exception.InvalidOperationException;

public class Agency
{
	private 		String 		name 		= null;
	private static	String      account		= null;
	private static	HeadQuarter headQuarter = null;
	private static	Agency		agency_1	= null;
	private static	Scanner		sc			= null;

	private final int OFFICE_COUNTER = 4;

	private final BlockingQueue<Runnable> waitingRoom =
			new ArrayBlockingQueue<Runnable>(50, true);

	private final ExecutorService officeCounter =
			Executors.newFixedThreadPool(OFFICE_COUNTER);

	private SerializableHashMap agency_accounts = null;

	protected Agency(String name)
	{
		this.name 				= name;
		this.agency_accounts 	= new SerializableHashMap();
	}

	public void addAccount(String name)
	{
		Integer number = agency_accounts.generateAccountNumber();
		agency_accounts.put(number, new Account(number, name));
	}

	public boolean accountExist(String name)
	{
		Account find = null;
		Iterator<Account> it = agency_accounts.values().iterator();
		
		while (it.hasNext())
		{
			find = it.next();
			if(find.getAccountHolder().equalsIgnoreCase(name))
			{
				return true;
			}
		}
		return false;
	}

	public Iterator<Account> getAccounts()
	{
		return agency_accounts.values().iterator();
	}

	private void officeCountersOpening()
	{
		System.out.println(" *** office counter opening... *** ");

		new Thread(new Runnable() {

			@Override
			public void run() {
				for(;;)
				{
					try {
						officeCounter.execute(waitingRoom.take());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}


	private static class SingletonOperatorHolder {
        public static final Operator INSTANCE =
        		new Operator("admin", agency_1, headQuarter.getBankAccount(),
        					null, TypeOperator.ADMINISTRATOR);
	}

	public static Operator getBankOperatorInstance() {
	        return SingletonOperatorHolder.INSTANCE;
	}

	private void waitingRoomOpening(int persons)
	{
		System.out.println(" *** waiting room opening... *** ");

		for(int p=0; p<persons; p++)
		{
			try {
				waitingRoom.put(new Persona(p));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void opening(int persons)
	{
		officeCountersOpening();

		waitingRoomOpening(persons);
	}

	public static void main(String[] args)
	{
		int persons = 0;//Integer.parseInt(args[0]);
		char c 		= 0;
		//int n_agencies = Integer.parseInt(args[1]);

		//System base bank creation
		headQuarter = HeadQuarter.getInstance();
		agency_1 = headQuarter.getNewAgency("Pomigliano");

		loadData();

		sc = new Scanner(System.in);

		printMenu();
		try {
			try{
				for(;;)
				{
					c = sc.next().charAt(0);
					switch(c)
					{
					case 'a':
						c = 0;
						adminMenu();
						break;

					case 'c':
						c = 0;
						clientMenu();
						break;

					case 'x':
						storeData();
						System.out.println("# Bank program Ended");
						System.exit(1);
						break;
					}
					printMenu();
				}
			} catch(InputMismatchException ime){
				System.err.println(ime.getMessage());
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

//		System.out.println(" --- " + persons + " are sitting in the waiting room ---");
//		agency_1.opening(persons);
	}

	/**
	 * load agency accounts from a stored file
	 */
	private static void loadData()
	{
		FileInputStream	  fin = null;
		ObjectInputStream ois = null;
		try {
			fin = new FileInputStream(".\\data.ser");
			ois = new ObjectInputStream(fin);

			agency_1.agency_accounts = (SerializableHashMap) ois.readObject();
			agency_1.agency_accounts.loadStoredValue();
			ois.close();
		} catch (FileNotFoundException e) {
			System.err.println("No stored account data founded");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		}

	}

	
	private static void storeData()
	{
		storeAccountData();
		headQuarter.storeFinancials();
	}
	/**
	 * store any accounts to a persistent file
	 */
	private static void storeAccountData()
	{
		FileOutputStream   fout = null;
		ObjectOutputStream oos  = null;
		try {
			fout = new FileOutputStream(".\\data.ser");
			oos  = new ObjectOutputStream(fout);

			oos.writeObject(agency_1.agency_accounts);
			oos.close();
			System.out.println("# Serialization Accounts Data: Done");

		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.err.println("# Serialization Accounts Data: Fail");
		}

	}

	public static void adminMenu() throws IOException
	{
		Operator   agency_operator = getBankOperatorInstance();
		Object[]   parameters 	   = null;
		Operation  op			   = null;
		int ch 					   = 0;
		account = "";

		printAdminMenu();

		do
		{
			ch = sc.next().charAt(0);
			parameters = new String[2];
			switch(ch)
			{
			case '0': //0 - Create account
				System.out.println("Write the name holder of new account:");
				parameters[0] = sc.nextLine();
				parameters[0] = sc.nextLine();
				op			  = new NewAccountOperation(agency_1);
				break;

			case '1': //1 - Select account by Name
				System.out.println("Select the account by name");
				parameters[0] = sc.nextLine();
				parameters[0] = sc.nextLine();
				if(!agency_operator.setWorkingAccount(agency_1.agency_accounts.get(agency_1.agency_accounts.getKeyByNameAccount((String) parameters[0]))))
				{
					System.err.println("Error: account name does not exist");
					account = "";
				} else {
					account = (String) parameters[0];
				}
				break;

			case '2': //2 - Select account by Number
				System.out.println("Select the account by number");
				parameters[0] = sc.nextLine();
				parameters[0] = sc.nextLine();
                try{
                    if(!agency_operator.setWorkingAccount(agency_1.agency_accounts.get(Integer.parseInt((String) parameters[0]))))
                    {
                    	System.err.println("Error: account number does not exist");
                    	account = "";
                    } else {
                    	account = (String) parameters[0];
                    }
                }catch(NumberFormatException nfe) {
                    System.err.println("Error: impossible read the number");
                }
				break;

			case '3': //3 - Deposit amount in the account
				System.out.println("Write: the amount to deposit on the selected account");
				parameters[0] = sc.nextLine();
				parameters[0] = (sc.nextLine()).replace(',', '.');
				op 			  = new DepositOperation();
				break;

			case '4': //4 - Withdrawal amount from the account
				System.out.println("Write: the amount to withdrawal from the selected account");
				parameters[0] = sc.nextLine();
				parameters[0] = (sc.nextLine()).replace(',', '.');
				op 			  = new WithdrawalOperation();
				break;

			case '5': //5 - Buy Financial Item from the bank
				parameters = new Object[2];
				System.out.println("Write: the financial item ID to buy");
				try {
					parameters[0] = sc.nextInt();
					parameters[0] = headQuarter.getFinancialItemByID((Integer)parameters[0]);
					parameters[1] = sc.nextLine();
					op			  = new BuyOperation();
				} catch(BankException be) {
					System.err.println(be.getMessage());
				} catch(InputMismatchException ime) {
					System.err.println("Error: not recognized input");
				}
				break;

			case '6': //6 - Sell Financial Item to the bank
				parameters = new Object[2];
				System.out.println("Write: the financial item ID to sell");
				try {
					parameters[0] = sc.nextInt();
					parameters[0] = headQuarter.getFinancialItemByID((Integer)parameters[0]);
					parameters[1] = sc.nextLine();
					op			  = new SellOperation();
				} catch(BankException be) {
					System.err.println(be.getMessage());
				} catch(InputMismatchException ime) {
					System.err.println("Error: not recognized input");
				}
				break;

			case '7': //7 - Activate account
				op = new EnableOperation();
				break;

			case '8': //8 - DeActivate account
				op = new DisableOperation();
				break;

			case '9': //9 - Bank transfer to other client
				try{
					parameters    = new Object[2];
					System.out.println("Write the destination account number, the amount to deposite on");
					sc.nextLine();
					parameters[0] = agency_1.agency_accounts.getAccountByNumber(sc.next()); //destination Account
					parameters[1] = (sc.next()).replace(',', '.');							//amount to transfer
					op			  = new TransfertOperation();
				} catch(NumberFormatException nfe) {
	                System.err.println("Error: impossible to read the number");
	            }
				break;

			case 'a': //a - List all the financial item
				parameters[0] = "";
				break;

			case 'b': //b - List all the account
				parameters[0] = "";
				op			  = new ListAccountOperation(agency_1);
				break;

            case 'l': //list bank financial item
            	parameters    = new Object[1];
                parameters[0] = headQuarter.getFinancialIterator();
                op            = new ListFinancialOperation();
                break;

			case 'p': //p - print history transaction
				parameters[0] = "";
				op 			  = new ShowHistoryOperation();
				break;
				
			case 'x': //exit to up level
				parameters = null;
				break;

			case 'm': //m - show the menu
				parameters = null;
				op		   = null;
				break;

			default:
				System.err.println("Selected option not available");
				ch = '1'; //to print: m - show the menu
			}

			printSelectedAccount();
			if((parameters != null) && (op != null))
			{
				agency_operator.setOperation(op);
				try{
					
					agency_operator.execOperation(parameters);
					
					op 		   = null;
					parameters = null;
					
				} catch (InvalidArgumentException iae) {
					System.err.println(iae.getMessage());
				} catch (InvalidOperationException ioe) {
					System.err.println(ioe.getMessage());
				}
				System.out.print("m - Show the menu\n");
			} else {
				if((ch != 'x') && (ch != '1') && (ch != '2'))
				{
					printAdminMenu();
				} else {
					System.out.print("m - Show the menu\n");
				}
			}
			
		} while(ch != 'x');
		account = "";
		clearScreen();
	}

	private static void printSelectedAccount() 
	{
		System.out.println("Selected account: " + account);
	}

	public static void clearScreen()
	{
		System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		//System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
	}

	public static void printAdminMenu()
	{
		clearScreen();
		printSelectedAccount();
		System.out.print(  " *** Admin Menu *** \n" +
						   " 0 - Create account \t\t\t| "+
				           " 1 - Select account by Name \n" +
						   " 2 - Select account by Number \t\t| " +
		                   " 3 - Deposit amount in the account \n" +
		                   " 4 - Withdrawal amount from the account | " +
		                   " 5 - Buy Financial Item from the bank \n" +
		                   " 6 - Sell Financial Item to the bank \t| " +
		                   " 7 - Activate account\n" +
		                   " 8 - DeActivate account\t\t\t| " +
						   " 9 - Bank transfer to other client\n" +
						   " l - List all the financial item\t| " +
						   " b - List all the account\n" +
						   " p - print history transaction\t\t| " +
						   " m - Show the menu\n" +
		                   " x - Exit\n >>");
	}

	public static void printMenu()
	{
		clearScreen();
		System.out.print(  " *** Main Menu *** \n" +
						   " a - Admin menu \n"+
				           " c - Client menu\n" +
		                   " x - Exit\n >>");
	}

	public static void clientMenu()
	{
		Operator client_operator = new Operator("client", agency_1, headQuarter.getBankAccount(), null, TypeOperator.CLIENT);
		Object[] parameters 	 = null;
		Operation op			 = null;
		int ch 					 = 0;

		printClientMenu();
		do
		{
			ch = sc.next().charAt(0);
			parameters = new String[2];
			switch(ch)
			{
			case '0': //0 - Create account
				System.out.println("Select the account by name");
				parameters[0] = sc.nextLine();
				parameters[0] = sc.nextLine();
				if(!client_operator.setWorkingAccount(agency_1.agency_accounts.get(agency_1.agency_accounts.getKeyByNameAccount((String) parameters[0]))))
				{
					System.err.println("Error: account name does not exist");
					account = "";
				} else {
					account = (String) parameters[0];
				}
				break;

			case '1': //3 - Deposit amount in the account
				System.out.println("Write: the amount to deposit on the selected account");
				parameters[0] = sc.nextLine();
				parameters[0] = (sc.nextLine()).replace(',', '.');
				op 			  = new DepositOperation();
				break;

			case '2': //4 - Withdrawal amount from the account
				System.out.println("Write: the amount to withdrawal from the selected account");
				parameters[0] = sc.nextLine();
				parameters[0] = (sc.nextLine()).replace(',', '.');
				op 			  = new WithdrawalOperation();
				break;

			case '3': //9 - Bank transfer to other client
				try{
					parameters = new Object[2];
					System.out.println("Write the destination account number, the amount to deposite on");
					sc.nextLine();
					parameters[0] = agency_1.agency_accounts.getAccountByNumber(sc.next()); //NumberFormatException destination Account
					parameters[1] = (sc.next()).replace(',', '.');							//amount to transfer
					op			  = new TransfertOperation();
				} catch(NumberFormatException nfe){
					System.err.println("Error: impossible to read the number");
				}
				break;

			case 's': //m - show balance
				op = new ShowBalanceOperation();
				break;

			case 'm': //m - show the menu
				parameters = null;
				op		   = null;
				break;
			
			case 'x': //exit to up level
				parameters = null;
				break;

			default:
				System.err.println("Selected option not available");
				ch = '0'; //to print: m - show the menu
			}

			printSelectedAccount();
			if((parameters != null) && (op != null))
			{
				client_operator.setOperation(op);
				try{
					op = null;
					client_operator.execOperation(parameters);
					parameters = null;
				} catch (InvalidArgumentException iae) {
					System.err.println(iae.getMessage());
				} catch (InvalidOperationException ioe) {
					System.err.println(ioe.getMessage());
				}
				System.out.print("m - Show the menu\n");
			} else {
				if((ch != 'x' ) && (ch != '0'))
				{
					printClientMenu();
				} else {
					System.out.print("m - Show the menu\n");
				}
			}
		} while(ch != 'x');
		account = "";
		clearScreen();
	}

	public static void printClientMenu()
	{
		clearScreen();
		printSelectedAccount();
		System.out.print  (" *** Client Menu *** \n" +
				           " 0 - Select account by Name\t\t| " +
		                   " 1 - Deposit amount in the account\n" +
		                   " 2 - Withdrawal amount from the account | " +
						   " 3 - Bank transfer to other client\n" +
						   " m - Show the menu\t\t\t| " +
						   " s - Show Balance\n" +
						   " x - Exit\n>>");
	}

}
