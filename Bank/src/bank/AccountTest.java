package bank;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountTest {

	static private HeadQuarter hq = null;
	static private Agency 	   a1 = null; 
	
	public Account	a 		= null;
	public Random	r 		= null;
	public Float    balance = null;
	public Integer  number	= null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		hq = HeadQuarter.getInstance();	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		r		= new Random();
		balance = new Float(r.nextInt(100));
		number  = new Integer(10000); 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAccountIntegerString() {
		a1 = hq.getNewAgency("name");
		a1.addAccount("a"+r.nextInt(100));
		a1.addAccount("a"+r.nextInt(100));
		a1.addAccount("a"+r.nextInt(100));
		
		Iterator<Account> it = a1.getAccounts();
		while(it.hasNext()){
			Account a = it.next();
			assertNotNull(a.getAccountHolder());
			assertNotNull(a.getAccountNumber());
			assertFalse(a.getActiveStatus());
		}
	}

	@Test
	public void testAccountIntegerStringFloatBoolean() {
		a1 = hq.getNewAgency("name2");
		
		testGetAccountHolder();
		testGetAccountBalance();
	}

	@Test
	public void testGetActiveStatus() {
		
		a =	new Account(number, "a"+r.nextInt(100), balance, new Boolean(true));
		assertTrue(a.getActiveStatus());
	}

	@Test
	public void testSetActiveStatus() {
		
		a =	new Account(number, "a"+r.nextInt(100), balance, new Boolean(true));
		assertTrue(a.getActiveStatus());
		a.setActiveStatus(false);
		assertFalse(a.getActiveStatus());
	}

	@Test
	public void testGetAccountNumber() {
		a =	new Account(number, "a"+r.nextInt(100), balance, new Boolean(true));
		assertEquals(a.getAccountNumber(), number);
		Account b = new Account(++number, "same number");
		
		assertEquals(b.getAccountNumber(), number);
	}

	@Test
	public void testGetAccountBalance() {
		a =	new Account(number, "a"+r.nextInt(100), balance, new Boolean(true));
		assertEquals(balance, a.getAccountBalance());
	}

	@Test
	public void testSetAccountBalance() {
		a =	new Account(number, "a"+r.nextInt(100), balance, new Boolean(true));
		assertEquals(balance, a.getAccountBalance());
		balance *=2;
		a.setAccountBalance(balance);
		assertEquals(balance, a.getAccountBalance());
		balance = (float) -5.0;
		a.setAccountBalance(balance);
		assertNotSame(balance, a.getAccountBalance());
		
	}

	@Test
	public void testGetAccountHolder() {
		String name = "a"+r.nextInt(100);
		a =	new Account(number, name , balance, new Boolean(true));
		assertEquals(name, a.getAccountHolder());
		
		name = "by_agency";
		a = new Account(number, name);
		assertEquals(name, a.getAccountHolder());
		
		name = "";
		a = new Account(number, name);
		assertNotSame(name, a.getAccountHolder());
	}

}
