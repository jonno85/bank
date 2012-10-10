package bank;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Random;

import operation.Operation;
import operation.SellOperation;
import operation.ShowHistoryOperation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperatorTest {

	static private HeadQuarter hq = null;
	static private Agency 	   a1 = null; 
	
	public Account	a 		= null;
	public Random	r 		= null;
	public Float    balance = null;
	public Integer  number	= null;
	public Operator op		= null;
	public Agency 	ag		= null;
	public TypeOperator type = null;
	public String name		= null;
	public Operation oper	= null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		hq = HeadQuarter.getInstance();	
	}

	@Before
	public void setUp() throws Exception {
		r		= new Random();
		balance = new Float(r.nextInt(100));
		number  = new Integer(10000); 
		
		type	= TypeOperator.ADMINISTRATOR;
		
		name = "a"+r.nextInt(100);
		ag = hq.getNewAgency(name);
		a = new Account(100,"zzz");
		op = new Operator(name, ag, hq.getBankAccount(), a, type);
	}

	@Test
	public void testOperatorStringAgencyAccountAccountTypeOperator() {
		assertNotNull(op);
		op = null;
		assertNull(op);
		op = new Operator(name, ag, hq.getBankAccount(), a, type);
		assertNotNull(op);
	}

	@Test
	public void testGetType() {
		assertEquals(type, op.getType());
	}

	@Test
	public void testSetOperation() {
		oper = new SellOperation();
		assertEquals(null, op.getOperation());
		op.setOperation(oper);
		assertEquals(oper, op.getOperation());
		oper = new ShowHistoryOperation();
		assertNotSame(oper, op.getOperation());
		op.setOperation(oper);
		assertEquals(oper, op.getOperation());
		
	}

	@Test
	public void testSetWorkingAccount() {
		a = new Account(0000, "___");
		op.setWorkingAccount(a);
		assertEquals(op.getWorkingAccount(), a);
	}

	@Test
	public void testGetWorkingAccount() {
		assertNotNull(op.getWorkingAccount());
		assertFalse(op.setWorkingAccount(null));
		
		
	}

	@Test
	public void testGetHistoryOperator() {
		Iterator<String> it = op.getHistoryOperator();
		assertNotNull(it);
	}

	@Test
	public void testGetBankAccount() {
		assertEquals(op.getBankAccount(), hq.getBankAccount());
	}

}
