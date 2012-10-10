package bank;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
/*
import org.junit.Rule;
import org.junit.rules.ExpectedException;
*/

import financialItem.FinancialItem;

public class HeadQuarterTest {

	static private HeadQuarter hq = null;
	
	//@Rule
	//public ExpectedException exception = ExpectedException.none();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		hq = HeadQuarter.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInstance() {
		hq = null;
		hq = HeadQuarter.getInstance();
		assertNotNull(hq);
	}


	@Test(expected=BankException.class)
	public void testGetNewAgency() {
		Agency a1 = hq.getNewAgency("name");
		assertNotNull(a1);
		Agency a2 = hq.getNewAgency("name");
	}

	@Test
	public void testGetBankAccount() {
		Account a = hq.getBankAccount();
		assertNotNull(a);
		Account b = hq.getBankAccount();
		assertSame(a, b);
	}

	@Test
	public void testGetFinancialIterator() {
		Iterator it = hq.getFinancialIterator();
		assertNotNull(it);
	}

	@Test
	public void testGetFinancialItemByID() {
		Random r = new Random();
		for(int i=0; i<HeadQuarter.BANK_PORTFOLIO/2; i++){
			int c = r.nextInt(HeadQuarter.BANK_PORTFOLIO);
			assertNotNull(hq.getFinancialItemByID(c));
		}
		
		
		testGetFinancialItemByIDFail();
	}
	
	@Test
	public void testGetFinancialItemByIDFail() {
		FinancialItem fi = null;
		Random r = new Random();
		for(int i=1; i<HeadQuarter.BANK_PORTFOLIO/2; i++){
			int c = HeadQuarter.BANK_PORTFOLIO + r.nextInt(HeadQuarter.BANK_PORTFOLIO) + i;
			fi = hq.getFinancialItemByID(c);
			assertNull(fi);
		}
	}

	@Test
	public void testIsFinancialItemExist() {
		Random r = new Random();
		
		for(int i=0; i<HeadQuarter.BANK_PORTFOLIO/2; i++){
			int c = r.nextInt(HeadQuarter.BANK_PORTFOLIO);
			assertTrue(hq.isFinancialItemExist(hq.getFinancialItemByID(c)));
		}
		
		testIsFinancialItemExistFail();
	}
	
	@Test
	public void testIsFinancialItemExistFail(){
		Random r = new Random();
		
		for(int i=1; i<HeadQuarter.BANK_PORTFOLIO/2; i++){
			int c = HeadQuarter.BANK_PORTFOLIO + r.nextInt(HeadQuarter.BANK_PORTFOLIO) + i;
			assertFalse(hq.isFinancialItemExist(hq.getFinancialItemByID(c)));
		}
	}

}
