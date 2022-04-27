package uo.ri.cws.domain;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;


public class VoucherPer500InvoiceTest {

	private Intervention i;
	private WorkOrder a;
	private Cash cash;

	@Before
	public void setUp() throws Exception {
		Client c = new Client("123", "n", "a");
		cash = new Cash( c );
		Mechanic m = new Mechanic("123a");
		Vehicle v = new Vehicle("123-ABC");
		VehicleType tv = new VehicleType("v", 300 /* €/hour */);
		Associations.Classify.link(tv, v);
		
		a = new WorkOrder(v, "for test");
		a.assignTo( m );
		i = new Intervention(m, a, 83 /* min */); // gives 500 €
	}
	
	
	/**
	 * Invoice over 500, paid and unused.
	 * Invoice should be available to generate a voucher.
	 */
	@Test
	public void testCanGenerateVoucher()  {
		a.markAsFinished();
		Invoice f = new Invoice(123L, Arrays.asList(a) );
		new Charge(f, cash, f.getAmount());
		f.settle(); // paid invoice
		
		assertTrue( f.canGenerate500Voucher() );
		f.markAsUsed();
	}
	
	/**
	 * Invoice over 500 but unused.
	 * Invoice should not be available to generate a voucher nor marked 
	 * as used.
	 * @throws BusinessException
	 */
	@Test(expected=IllegalStateException.class)
	public void testMas500NoPagadaGenerateVoucher(){
		a.markAsFinished();
		Invoice f = new Invoice(123L, Arrays.asList(a) ); // unpaid
		assertTrue( f.canGenerate500Voucher() == false );
		f.markAsUsed();
	}
	
	/**
	 * Invoice under 500 and unpaid.
	 * Invoice should not be available to generate a voucher nor marked 
	 * as used.
	 */
	@Test(expected=IllegalStateException.class)
	public void testLess500CannotGenerateVoucher(){
		i.setMinutes(82); // gives 499 €
		a.markAsFinished();
		Invoice f = new Invoice(123L, Arrays.asList(a) );
		assertTrue( f.canGenerate500Voucher() == false );
		f.markAsUsed();

	}
	
	/**
	 * Invoice under 500 but paid.
	 * Invoice should not be available to generate a voucher nor marked 
	 * as used.
	 */
	@Test(expected=IllegalStateException.class)
	public void testLess500PaidGenerateVoucher() {
		i.setMinutes( 82 /* min */ ); // gives 499 €
		a.markAsFinished();
		Invoice f = new Invoice(123L, Arrays.asList(a) );
		new Charge(f, cash, f.getAmount());
		f.settle(); 	// paid
		
		assertTrue( f.canGenerate500Voucher() == false );
		f.markAsUsed();

	}
	
	/**
	 * Invoice over 500 and paid, but already used.
	 * Invoice should not be available to generate a voucher nor marked 
	 * as used.
	 */
	@Test(expected=IllegalStateException.class)
	public void testMarkAsBono500Used() {
		a.markAsFinished();
		Invoice f = new Invoice(123L, Arrays.asList(a) );
		new Charge(f, cash, f.getAmount());
		f.settle(); 	// paid
		f.markAsUsed();

		assertTrue( f.canGenerate500Voucher() == false );
		assertTrue( f.isUsedForVoucher());
		f.markAsUsed();
	}

}
