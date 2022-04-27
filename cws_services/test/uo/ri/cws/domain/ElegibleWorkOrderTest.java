package uo.ri.cws.domain;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;


public class ElegibleWorkOrderTest {

	private Client c;
	private Vehicle v;
	private Mechanic m;
	private WorkOrder a;

	@Before
	public void setUp() throws Exception {
		c = new Client("123a", "n", "a");
		m = new Mechanic("123a");
		v = new Vehicle("123-ABC");
		VehicleType tv = new VehicleType("tv", 30 /* €/hour */);
		
		Associations.Own.link(c,  v);
		Associations.Classify.link(tv, v);
		
		a = new WorkOrder(v, "for test");
		a.assignTo(m);
		new Intervention(m, a, 10);
		a.markAsFinished();
	}
	
	/**
	 * A not invoiced workOrder cannot be used for a voucher
	 */
	@Test
	public void testWokrOrderNotInvoiced() {
		assertTrue( a.canBeUsedForVoucher() == false );
	}
	
	/**
	 * An invoiced but unpaid workOrder cannot be used for a voucher
	 */
	@Test
	public void testWorkOrderInvoicedButUnpaid()  {
		new Invoice(123L, Arrays.asList( a )); 
		assertTrue( a.canBeUsedForVoucher() == false );
	}
	
	/**
	 * An invoiced and paid workOrder can be used for a voucher
	 */
	@Test
	public void testWorkOrderInvoicedAndPaid()  {
		Invoice f = new Invoice(123L, Arrays.asList( a ));  
		Cash m = new Cash(c);
		new Charge(f, m, f.getAmount());
		f.settle();  
		
		assertTrue( a.canBeUsedForVoucher() );
	}
	
	/**
	 * an invoiced and paid workOrder, but already used for a voucher, cannot 
	 * be used again
	 */
	@Test
	public void testUsedWorkOrder() {
		Invoice f = new Invoice(123L, Arrays.asList( a ));  
		Cash m = new Cash(c);
		new Charge(f, m, f.getAmount());
		f.settle();  
		
		assertTrue( a.canBeUsedForVoucher() );
		a.markAsUsedForVoucher();
		assertTrue( a.canBeUsedForVoucher() == false );
	}
}
