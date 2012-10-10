/**
 * 
 */
package bank;

import static org.junit.Assert.*;

import java.util.Random;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author F31999A
 *
 */
public class AgencyTest {

	static private Agency      ag = null;
	static private HeadQuarter hq = null;
	static private Operator    op = null;
	static private Operator    op1 = null;
	private        String[] names = null;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		hq = HeadQuarter.getInstance();
		ag = hq.getNewAgency("prova");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Random r = new Random();
		Integer counter = r.nextInt(50);
		names = new String[counter];
		while(counter > 0){
			String name = new String("account"+ counter.toString());
			--counter;
			names[counter] = new String(name);
			ag.addAccount(name);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link bank.Agency#Agency(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testAgency() {
		try {
			setUp();
			for(String s : names){
				assertTrue(ag.accountExist(s));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Test method for {@link bank.Agency#addAccount(java.lang.String)}.
	 */
	@Test
	public void testAddAccount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bank.Agency#accountExist(java.lang.String)}.
	 */
	@Test
	public void testAccountExist() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bank.Agency#getAccounts()}.
	 */
	@Test
	public void testGetAccounts() {
		
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bank.Agency#getBankOperatorInstance()}.
	 */
	@Test
	public void testGetBankOperatorInstance() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bank.Agency#adminMenu()}.
	 */
	@Test
	public void testAdminMenu() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bank.Agency#clientMenu()}.
	 */
	@Test
	public void testClientMenu() {
		fail("Not yet implemented");
	}

}
