package uo.ri.cws.domain;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import alb.util.random.Random;

public class VoucherPer3RecommendationsTest {

	private Client c;
	private Client cr1; // client recommended 1
	private Client cr2; // client recommended 2
	private Client cr3; // client recommended 3
	private Client cr4; // client recommended 4
	private Mechanic m;
	private long nextInvoice;

	@Before
	public void setUp() throws Exception {
		c = new Client("123a", "n", "a");
		cr1 = new Client("234b", "nr1", "ar1");
		cr2 = new Client("345c", "nr2", "ar2");
		cr3 = new Client("456d", "nr3", "ar3");
		cr4 = new Client("567e", "nr4", "ar4");
		m = new Mechanic("678f");
		nextInvoice = 1;
	}
	
	/**
	 * Fresh client, no recommendation nor vehicles.
	 * Client should not be eligible.
	 */
	@Test
	public void testFreshClient() {
		assertTrue( c.eligibleForRecommendationVoucher() == false );
	}
	
	/**
	 * Client with vehicle, but no workorders.
	 * Client should not be eligible.
	 */
	@Test
	public void testClientNoVehicle() {
		addVehicle( c );		
		assertTrue( c.eligibleForRecommendationVoucher() == false );
	}
	
	
	
	/**
	 * Client with workorders but no recommendations.
	 * Client should not be eligible.
	 */
	@Test
	public void testClientNoRecommendations() {
		addVehicleWithWorkOrder(c, m);
		
		assertTrue( c.eligibleForRecommendationVoucher() == false );
	}
	
	/**
	 * Client without workorders but with 3 recommendations
	 * Client should not be eligible.
	 */
	@Test
	public void testClientRecommendationsButNoWOs() {
		recommend(c, cr1);
		recommend(c, cr2);
		recommend(c, cr3);
		addVehicle( c );
		addVehicleWithWorkOrder(cr1, m);
		addVehicleWithWorkOrder(cr2, m);
		addVehicleWithWorkOrder(cr3, m);
		
		assertTrue( c.eligibleForRecommendationVoucher() == false );
	}
	
	/**
	 * Client with workorders and 3 recommendations, but the recommended 
	 * clients don't have workorders.
	 * Client should not be eligible.
	 */
	@Test
	public void testClientRecommmendationsWithNoWOs() {
		recommend(c, cr1);
		recommend(c, cr2);
		recommend(c, cr3);
		addVehicleWithWorkOrder(c, m);
		addVehicle(cr1);	// <-- No WorkOrder
		addVehicle(cr2);	// <-- No WorkOrder
		addVehicle(cr3);	// <-- No WorkOrder
		
		assertTrue( c.eligibleForRecommendationVoucher() == false );
	}
	
	/**
	 * Client with workorders, 2 recommendations with workorders and 1 
	 * recommendation without workorders
	 * Client should not be eligible.
	 */
	@Test
	public void testClientReccommendationsNotEnoughWOs() {
		recommend(c, cr1);
		recommend(c, cr2);
		recommend(c, cr3);
		addVehicleWithWorkOrder(c, m); // workOrder is not paid.
		addVehicleWithWorkOrder(cr1, m); // workOrder is not paid.
		addVehicleWithWorkOrder(cr2, m); // workOrder is not paid.
		addVehicle(cr3);	// <-- No WorkOrder
		
		assertTrue( c.eligibleForRecommendationVoucher() == false );
	}
	
	/**
	 * Client has vehicle with unpaid workorders and 3 recommendations with 
	 * unpaid workorders.
	 * Client should not be elegible
	 */
	@Test
	public void testClientUnpaidWOs() {
		recommend(c, cr1);
		recommend(c, cr2);
		recommend(c, cr3);
		addVehicleWithWorkOrder(c, m);
		addVehicleWithWorkOrder(cr1, m);
		addVehicleWithWorkOrder(cr2, m);
		addVehicleWithWorkOrder(cr3, m);
		
		assertTrue( c.eligibleForRecommendationVoucher() == false);
	}
	
	/**
	 * Client has vehicle with unpaid workorders and 3 recommendations with 
	 * paid workorders.
	 * Client should not be elegible
	 */
	@Test
	public void testClientUnpadiWOsPaidRecommendations() {
		recommend(c, cr1);
		recommend(c, cr2);
		recommend(c, cr3);
		addVehicleWithWorkOrder(c, m);
		addVehicleWithPaidWorkOrder(cr1, m);  // workorder is paid
		addVehicleWithPaidWorkOrder(cr2, m);  // workorder is paid
		addVehicleWithPaidWorkOrder(cr3, m);  // workorder is paid
		
		assertTrue( c.eligibleForRecommendationVoucher() == false);
	}
	
	/**
	 * Client has vehicle with paid workorders and 3 recommendations with 
	 * unpaid workorders.
	 * Client should not be elegible
	 */
	@Test
	public void testClientPaidWOsUnpaidRecommendations() {
		recommend(c, cr1);
		recommend(c, cr2);
		recommend(c, cr3);
		addVehicleWithPaidWorkOrder(c, m);
		addVehicleWithWorkOrder(cr1, m);
		addVehicleWithWorkOrder(cr2, m);
		addVehicleWithWorkOrder(cr3, m);
		
		assertTrue( c.eligibleForRecommendationVoucher() == false);
	}
	
	/**
	 * Client has vehicle with paid workOrders, 2 recommendations with 
	 * unpaid workorders 
	 * and 1 recommendation with paid workOrders
	 * Client should not be elegible
	 */
	@Test
	public void testClientPaidWOsNotuEnoudhPaidRecommendations() {
		recommend(c, cr1);
		recommend(c, cr2);
		recommend(c, cr3);
		addVehicleWithPaidWorkOrder(c, m);
		addVehicleWithPaidWorkOrder(cr1, m);
		addVehicleWithWorkOrder(cr2, m);
		addVehicleWithWorkOrder(cr3, m);
		
		assertTrue( c.eligibleForRecommendationVoucher() == false);
	}
	
	/**
	 * Client has vehicle with paid workOrders and 3 recommendations with 
	 * paid workorders 
	 * Client should be elegible
	 */
	@Test
	public void testClientPadiWOsPaidRecommendations() {
		recommend(c, cr1);
		recommend(c, cr2);
		recommend(c, cr3);
		addVehicleWithPaidWorkOrder(c, m);
		addVehicleWithPaidWorkOrder(cr1, m);
		addVehicleWithPaidWorkOrder(cr2, m);
		addVehicleWithPaidWorkOrder(cr3, m);
		
		assertTrue( c.eligibleForRecommendationVoucher());
	}
	
	/**
	 * Client has paid workOrders. client has also 3 paid recommendations, 
	 * but one is already used 
	 * Client should not be elegible
	 */
	@Test
	public void testClientPaidWOsPaidRecommendationsBut1Used() {
		Recommendation r = recommend(c, cr1);
		recommend(c, cr2);
		recommend(c, cr3);
		addVehicleWithPaidWorkOrder(c, m);
		addVehicleWithPaidWorkOrder(cr1, m);
		addVehicleWithPaidWorkOrder(cr2, m);
		addVehicleWithPaidWorkOrder(cr3, m);
		
		r.markAsUsed();
		
		assertTrue( c.eligibleForRecommendationVoucher() == false);
	}
	
	/**
	 * Client has paid workOrders. client has also 2 paid and unused 
	 * recommendations, 1 paid and used, and 1 unpaid and unused
	 * Client should not be eligible
	 */
	@Test
	public void testClientPaidWOsNotEnoughEligibleRecommendations() {
		Recommendation r = recommend(c, cr1);
		recommend(c, cr2);
		recommend(c, cr3);
		recommend(c, cr4);
		addVehicleWithPaidWorkOrder(c, m);
		addVehicleWithPaidWorkOrder(cr1, m);
		addVehicleWithPaidWorkOrder(cr2, m);
		addVehicleWithPaidWorkOrder(cr3, m);
		addVehicleWithWorkOrder(cr4, m);
		
		r.markAsUsed();
		
		assertTrue( c.eligibleForRecommendationVoucher() == false);
	}
	
	
	
	private Vehicle addVehicleWithPaidWorkOrder(
			Client client, 
			Mechanic mechanic) {
		Vehicle v = addVehicle(client);
		addWorkOrder(v, mechanic);
		payWorkOrders(v);
		return v;
	}
	
	private Invoice payWorkOrders(Vehicle vehicle) {
		Invoice invoice = new Invoice(
				nextInvoice++, 
				new ArrayList<WorkOrder>(vehicle.getWorkOrders())
				);
		invoice.settle();
		return invoice;
	}
	
	private Vehicle addVehicleWithWorkOrder(
			Client client, 
			Mechanic mechanic) {
		Vehicle v = addVehicle(client);
		addWorkOrder(v, mechanic);
		return v;
	}
	
	private Vehicle addVehicle(Client client) {
		Vehicle v = new Vehicle( Random.string( 7 ));
		Associations.Own.link(client, v);
		return v;
	}
	
	private WorkOrder addWorkOrder(Vehicle vehicle,
			Mechanic mechanic) {
		sleep( 10 /*msec*/ );
		WorkOrder w = new WorkOrder( vehicle );
		w.assignTo( mechanic );
		w.markAsFinished();
		return w;
	}
	
	
	
	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// dont't care if this occurs
		}
	}
	
	private Recommendation recommend(Client sponsor, 
			Client recommended) {
		return new Recommendation(sponsor, recommended);
	}
	
	

}
