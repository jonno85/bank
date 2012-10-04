package bank;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.InputMismatchException;
import java.util.Iterator;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import operation.DepositOperation;
import operation.DisableOperation;
import operation.EnableOperation;
import operation.InvalidArgumentException;
import operation.InvalidOperationException;
import operation.ListAccountOperation;
import operation.NewAccountOperation;
import operation.Operation;
import operation.TransfertOperation;
import operation.WithdrawalOperation;

public class Agency
{
	private 		String 		name 		= null;
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
		return agency_accounts.containsValue(name);
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
		int persons = Integer.parseInt(args[0]);
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
						System.out.println("Bank program Ended");
						System.exit(1);
						break;					
					}
					printMenu();
				}
			} catch(InputMismatchException ime){
				ime.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		System.out.println(" --- " + persons + " are sitting in the waiting room ---");
		
		
		
		
		
		agency_1.opening(persons);
	}
	
	/**
	 * load agency accounts from a stored file
	 */
	private static void loadData()
	{
		FileInputStream	  fin = null;
		ObjectInputStream ois = null;
		try {
			fin = new FileInputStream("\\data.ser");
			ois = new ObjectInputStream(fin);
			
			agency_1.agency_accounts = (SerializableHashMap) ois.readObject();
			agency_1.agency_accounts.loadStoredValue();
			ois.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.err.println("No stored account data founded");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * store any accounts to a persistent file
	 */
	private static void storeData()
	{
		FileOutputStream   fout = null;
		ObjectOutputStream oos  = null;
		try {
			fout = new FileOutputStream("\\data.ser");
			oos  = new ObjectOutputStream(fout);
			
			oos.writeObject(agency_1.agency_accounts);
			oos.close();
			System.out.println("#Serialization: Done");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void adminMenu() throws IOException
	{
		Operator agency_operator = getBankOperatorInstance();
		Object[] parameters 	 = null;
		Operation op			 = null;
		int ch 					 = 0;
		
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
				agency_operator.setWorkingAccount(agency_1.agency_accounts.get(agency_1.agency_accounts.getKeyByNameAccount(sc.nextLine())));
				break;
				
			case '2': //2 - Select account by Number 
				System.out.println("Select the account by number");
				parameters[0] = sc.nextLine();
				agency_operator.setWorkingAccount(agency_1.agency_accounts.get(Integer.parseInt(sc.nextLine())));
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
				System.out.println("Write: the financial item ID to buy to the selected account");
				parameters[0] = sc.nextLine();
				parameters[0] = sc.nextLine();
				break;
				
			case '6': //6 - Sell Financial Item to the bank
				System.out.println("Write: the financial item ID to sell from the selected account");
				parameters[0] = sc.nextLine();
				parameters[0] = sc.nextLine();
				break;
				
			case '7': //7 - Activate account
				op = new EnableOperation();
				break;
				
			case '8': //8 - DeActivate account
				op = new DisableOperation();
				break;
				
			case '9': //9 - Bank transfer to other client
				parameters = new Object[2];
				System.out.println("Write the destination account number, the amount to deposite on");
				sc.nextLine();
				parameters[0] = agency_1.agency_accounts.getAccountByNumber(sc.next()); //destination Account
				parameters[1] = (sc.next()).replace(',', '.');							//amount to transfer
				op			  = new TransfertOperation();
				
				break;
				
			case 'a': //a - List all the financial item
				parameters[0] = "";
				break;
				
			case 'b': //b - List all the account				
				parameters[0] = "";
				op			  = new ListAccountOperation(agency_1);
				break;
				
			case 'p': //p - print history transaction
				parameters[0] = "";
				break;
				
			case 'm': //m - show the menu
				parameters = null;
				op		   = null;
				break;
				
			default:
				System.out.println("Selected option not available");
			}
			
			if((parameters != null) && (op != null))
			{
				agency_operator.setOperation(op);
				try{
					op = null;
					agency_operator.execOperation(parameters);
					parameters = null;
				} catch (InvalidArgumentException iae) {
					System.err.println(iae.getMessage());
				} catch (InvalidOperationException ioe) {
					System.err.println(ioe.getMessage());
				}
				System.out.print(" m - Show the menu\n");
			} else {
				printAdminMenu();
			}
		} while(ch != 'x');
		
		clearScreen();
	}
	
	public static void clearScreen()
	{
		System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}
	
	public static void printAdminMenu()
	{
		clearScreen();
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
						   " a - List all the financial item\t| " +
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
		int ch				= 0;
		String[] parameters = null;
		Operation op		= null;
		
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
				//agency_operator.setWorkingAccount(agency_1.agency_accounts.get(agency_1.agency_accounts.getKeyByNameAccount(sc.nextLine())));
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
				
			case '5': //5 - Buy Financial Item from the bank
				System.out.println("Write: the financial item ID to buy to the selected account");
				parameters[0] = sc.nextLine();
				parameters[0] = sc.nextLine();
				break;
				
			case '6': //6 - Sell Financial Item to the bank
				System.out.println("Write: the financial item ID to sell from the selected account");
				parameters[0] = sc.nextLine();
				parameters[0] = sc.nextLine();
				break;
				
			case 'm': //m - show the menu
				parameters = null;
				op		   = null;
				break;
				
			default:
				System.out.println("Selected option not available");
			}
			
			if((parameters != null) && (op != null))
			{
				//agency_operator.setOperation(op);
				try{
					op = null;
					//agency_operator.execOperation(parameters);
					
				} catch (InvalidArgumentException iae) {
					System.err.println(iae.getMessage());
				} catch (InvalidOperationException ioe) {
					System.err.println(ioe.getMessage());
				}
				System.out.print(" m - Show the menu\n");
			} else {
				printClientMenu();
			}
		} while(ch != 'x');
		
		clearScreen();
	}
	
	public static void printClientMenu()
	{
		clearScreen();
		System.out.print  (" *** Client Menu *** \n" +
				           " 0 - Select account by Name\t\t| " + 
		                   " 1 - Deposit amount in the account\n" +
		                   " 2 - Withdrawal amount from the account | " +
		                   " 3 - Buy Financial Item from the bank\n" + 
		                   " 4 - Sell Financial Item to the bank\t| " +
						   " 5 - Bank transfer to other client\n" +
						   " m - Show the menu\t\t\t| " +
						   " x - Exit\n>>");
	}
	
}
