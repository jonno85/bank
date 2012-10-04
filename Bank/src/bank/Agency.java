package bank;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import operation.DepositOperation;
import operation.DisableOperation;
import operation.EnableOperation;
import operation.ListAccountOperation;
import operation.NewAccountOperation;
import operation.Operation;
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
		Integer number = Utils.generateAccountNumber();
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
        		new Operator("admin", agency_1, headQuarter.getBankAccount());
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
		//int n_agencies = Integer.parseInt(args[1]);
		
		//System base bank creation
		headQuarter = HeadQuarter.getInstance();
		agency_1 = headQuarter.getNewAgency("Pomigliano");
		
		loadData();
		
		sc = new Scanner(System.in);
		
		char c = 0;
		printMenu();
		try {
			try{
				for(;;)
				{
					c = sc.next().charAt(0);
					//System.out.println("dentro: " + c);
					switch(c)
					{
					case 'a':
						adminMenu(c);
						c = 0;
						break;
					case 'c':
						System.out.flush();
						System.out.print("\n\n\n\n\n\n\n\n\n\n");
						printClientMenu();
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
	
	private static void loadData()
	{
		FileInputStream fin;
		try {
			fin = new FileInputStream("\\data.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			agency_1.agency_accounts = (SerializableHashMap) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	private static void storeData()
	{
		try {
			FileOutputStream fout = new FileOutputStream("\\data.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			
			oos.writeObject(agency_1.agency_accounts);
			oos.close();
			System.out.println("#Serialization: Done");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void adminMenu(int c) throws IOException
	{
		Operator agency_operator	= getBankOperatorInstance();
		String[] parameters 		= null;
		Operation op				= null;
		//Pattern myPattern = Pattern.compile("[\\n\\r]+");
		int ch = c;
		
		//System.out.print("\n\n\n\n\n\n\n\n\n\n");
		printAdminMenu();
		
		do
		{
			//System.out.flush();
			ch = sc.next().charAt(0);
			//sc.nextLine();
			//System.out.flush();
			
			switch(ch)
			{
			case '0': //0 - Create account
				parameters = new String[2];
				System.out.println("Write the name holder of new account:");
				parameters[0]	= sc.nextLine();
				parameters[1]	= sc.nextLine();
				parameters[0]   = parameters[1];
				op				= new NewAccountOperation(agency_1);
				//System.out.println("ancora una " + sc.hasNext() + "  " + sc.nextLine());
				break;
				
			case '1': //1 - Select account by Name
				System.out.println("Write the name account holder");
				parameters		= new String[1];
				parameters[0]   = sc.next();
				break;
				
			case '2': //2 - Select account by Number 
				System.out.println("Write the number account holder");
				parameters		= new String[1];
				parameters[0]   = sc.next();
				break;
				
			case '3': //3 - Deposit amount in the account
				sc.useDelimiter(" ");
				System.out.println("Write: the account number, the amount to deposit on:");
				parameters		= new String[2];
				parameters[0]	= sc.next();
				parameters[1]	= sc.next();
				op 				= new DepositOperation();
				break;
				
			case '4': //4 - Withdrawal amount from the account
				sc.useDelimiter(" ");
				System.out.println("Write: the account number, the amount to withdrawal:");
				parameters		= new String[2];
				parameters[0]	= sc.next();
				parameters[1]	= sc.next();
				op 				= new WithdrawalOperation();
				break;
				
			case '5': //5 - Buy Financial Item from the bank
				System.out.println("Write the financial item ID to buy");
				parameters		= new String[1];
				parameters[0]	= sc.next();
				break;
				
			case '6': //6 - Sell Financial Item to the bank
				System.out.println("Write the financial item ID to sell");
				parameters		= new String[1];
				parameters[0]	= sc.next();
				break;
				
			case '7': //7 - Activate account
				System.out.println("Write the account number to activate");
				parameters		= new String[1];
				parameters[0]	= sc.next();
				op				= new EnableOperation();
				break;
				
			case '8': //8 - DeActivate account
				System.out.println("Write the account number to deactivate");
				parameters		= new String[1];
				parameters[0]	= sc.next();
				op				= new DisableOperation();
				break;
				
			case '9': //9 - Bank transfer to other client
				sc.useDelimiter(" ");
				System.out.println("Write the destination account number, the amount to deposite on");
				parameters = new String[2];
				parameters[0] = sc.next();
				parameters[1] = sc.next();
				break;
				
			case 'a': //a - List all the financial item
				parameters		= new String[1];
				parameters[0]	= "";
				break;
				
			case 'b': //b - List all the account				
				parameters		= new String[1];
				parameters[0]	= "";
				op				= new ListAccountOperation(agency_1);
				break;
				
			case 'm': //m - show the menu
				parameters		= null;
				op				= null;
			default:
			}
			
			if((parameters != null) && (op != null))
			{
				agency_operator.setOperation(op);
				agency_operator.execOperation(parameters);
				System.out.print(" m - Show the menu\n");
			} else {
				System.out.print("\n\n\n\n\n\n\n\n\n\n");
				printAdminMenu();
			}
		} while ( ch != 'x');
	}
	
	public static void printAdminMenu()
	{
		System.out.print(  " *** Admin Menu *** \n" +
						   " 0 - Create account \n"+
				           " 1 - Select account by Name \n" + 
						   " 2 - Select account by Number \n" +
		                   " 3 - Deposit amount in the account \n" +
		                   " 4 - Withdrawal amount from the account \n" +
		                   " 5 - Buy Financial Item from the bank \n" + 
		                   " 6 - Sell Financial Item to the bank\n" +
		                   " 7 - Activate account\n" +
		                   " 8 - DeActivate account\n" +
						   " 9 - Bank transfer to other client\n" +
						   " a - List all the financial item\n" +
						   " b - List all the account\n" +
						   " m - Show the menu\n" +
		                   " x - Exit\n >>");
	}
	
	public static void printMenu()
	{
		System.out.print(  " *** Menu *** \n" +
						   " a - Admin account \n"+
				           " c - Select account by Name\n" + 
		                   " x - Exit\n >>");
	}
	
	public static void printClientMenu()
	{
		System.out.print  (" *** Client Menu *** \n" +
				           " 0 - Select account by Name\n" + 
		                   " 1 - Deposit amount in the account\n" +
		                   " 2 - Withdrawal amount from the account\n" +
		                   " 3 - Buy Financial Item from the bank\n" + 
		                   " 4 - Sell Financial Item to the bank\n" +
						   " 5 - Bank transfer to other client\n" +
						   " x - Exit\n>>");
	}
	
}
