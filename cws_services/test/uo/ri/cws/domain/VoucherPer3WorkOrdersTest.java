package uo.ri.cws.domain;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class VoucherPer3WorkOrdersTest {

	private Client c;
	private List<WorkOrder> as = new ArrayList<WorkOrder>();
	private Vehicle v;
	private Cash cash;
	private Mechanic m;
	private VehicleType tv;

	@Before
	public void setUp() throws Exception {
		c = new Client("123a", "n", "a");
		cash = new Cash( c );
		m = new Mechanic("123a");
		v = new Vehicle("123-ABC");
		tv = new VehicleType("tv", 30 /* €/hour */);

		Associations.Own.link(c,  v);
		Associations.Classify.link(tv, v);

		as.add( createWorkOrder(v, m) );
		as.add( createWorkOrder(v, m) );
		as.add( createWorkOrder(v, m) );		
	}


	private WorkOrder createWorkOrder(Vehicle v, Mechanic m) {
		sleep( 10 /*msec*/);   // Delay to avoid identhical dates.
		WorkOrder workOrder = new WorkOrder(v, "for test");
		workOrder.assignTo(m);
		new Intervention(m, workOrder, 10); //Link wokrOrder and mechanic
		workOrder.markAsFinished();
		return workOrder;
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// pass
		}
	}

	/**
	 * Client with 3 paid and unsused WorkOrders
	 * The 3 of them should be available to create a voucher
	 */
	@Test
	public void testGenerate3WorkOrdersVoucher()  {
		Invoice f = new Invoice(123L, as);  // workorders invoiced
		new Charge(f, cash, f.getAmount());	
		f.settle();							// invoice paid

		List<WorkOrder> averias = c.getWorkOrdersAvailableForVoucher();
		assertTrue( averias.size() == 3 );
		assertTrue( averias.stream().allMatch(av -> av.canBeUsedForVoucher()));
	}

	/**
	 * Client with 3 unpaid workorders
	 * There should not be any workorder available to create a voucher
	 */
	@Test
	public void test3AveriasNoPagadas() {
		Invoice f = new Invoice(123L, as);  // workorders invoiced
		new Charge(f, cash, f.getAmount());	
		// f.settle();						<-- invoice is not paid

		assertTrue( c.getWorkOrdersAvailableForVoucher().size() == 0 );
	}

	/**
	 * Client with 2 paid and unused workorders
	 * 2 invoices whould be availabel to create a voucher
	 */
	@Test
	public void testMenos3Averias()  {
		WorkOrder a = as.get(0);
		Associations.Fix.unlink(v, a);
		as.remove( a );						// Just 2 workOrders

		Invoice f = new Invoice(123L, as);  // WorkOrders invoiced
		new Charge(f, cash, f.getAmount());	
		f.settle();							// Invoice paid

		List<WorkOrder> averias = c.getWorkOrdersAvailableForVoucher();
		assertTrue( averias.size() == 2 );
		assertTrue( averias.stream().allMatch(av -> av.canBeUsedForVoucher()));
	}

	/**
	 * client with two paid and unusd workorders, and another unused but not 
	 * invoiced workorder
	 * 2 workorders should be available to create a voucher
	 */
	@Test
	public void test2AveriasPagadas1Nofacturada()  {
		as.remove( 0 );						// 2 worOrders, 1 not invoiced
		Invoice f = new Invoice(123L, as);  // workOrders invoiced
		new Charge(f, cash, f.getAmount());	
		f.settle();							// Invoice paid

		List<WorkOrder> averias = c.getWorkOrdersAvailableForVoucher();
		assertTrue( averias.size() == 2 );
		assertTrue( averias.stream().allMatch(av -> av.canBeUsedForVoucher()));
	}

	/**
	 * Client with two paid and unused workorders, and another one invoiced but
	 *  unpaid.
	 * 2 workorders should be available for voucher generation.
	 */
	@Test
	public void test2AveriasPagadas1NoPagada()  {
		WorkOrder a = as.get(0);				
		as.remove( 0 );						   // Solo dos averias
		Invoice f = new Invoice(123L, as);     // Averias pasan a facturadas
		new Charge(f, cash, f.getAmount());	
		f.settle();							   // Factura pasa a pagada
		new Invoice(234L, Arrays.asList( a )); // 1 no facturada, no pagada

		List<WorkOrder> averias = c.getWorkOrdersAvailableForVoucher();
		assertTrue( averias.size() == 2 );
		assertTrue( averias.stream().allMatch(av -> av.canBeUsedForVoucher()));
	}

	/**
	 * Client with 3 paid workorders. 2 are unused, the other one is used
	 * 2 workorders should be available to create a voucher
	 */
	@Test
	public void test3AveriasPagadas1Usada()  {
		WorkOrder a = as.get(0);				// Facturada aparte
		as.remove( 0 );						// Solo dos averias
		Invoice f1 = new Invoice(123L, as); // Averias pasan a facturadas
		new Charge(f1, cash, f1.getAmount());	
		f1.settle();						// Factura pasa a pagada

		Invoice f2 = new Invoice(234L, Arrays.asList( a )); // facturada
		new Charge(f2, cash, f2.getAmount());
		f2.settle(); 										// liquidada

		a.markAsUsedForVoucher();			// una se marca como usada bono3

		List<WorkOrder> averias = c.getWorkOrdersAvailableForVoucher();
		assertTrue( averias.size() == 2 );
		assertTrue( averias.stream().allMatch(av -> av.canBeUsedForVoucher()));
	}

	/**
	 * Client with 5 paid and unused workorders
	 * 5 workOrders should be available to create a voucher.
	 */
	@Test
	public void testMas3Averias() {
		as.add( createWorkOrder(v, m) );
		as.add( createWorkOrder(v, m) );  			// 5 averias

		Invoice f1 = new Invoice(123L, as); 	// Averias pasan a facturadas
		new Charge(f1, cash, f1.getAmount());	
		f1.settle();							// Factura pasa a pagada

		List<WorkOrder> averias = c.getWorkOrdersAvailableForVoucher();
		assertTrue( averias.size() == 5 );
		assertTrue( averias.stream().allMatch(av -> av.canBeUsedForVoucher()));
	}

}
