package uo.ri.cws.application.service.paymentmean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.service.common.TestContext;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.service.util.ClientUtil;
import uo.ri.cws.application.service.util.InvoiceUtil;
import uo.ri.cws.application.service.util.PaymentMeanUtil;
import uo.ri.cws.application.service.util.RecommendationUtil;
import uo.ri.cws.application.service.util.VehicleUtil;
import uo.ri.cws.application.service.util.WorkOrderUtil;
import uo.ri.cws.application.service.util.sql.FindClientByDniSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindVehicleByPlateSqlUnitOfWork;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;

public class GenerateVouchersSteps {

	private TestContext ctx;
	private List<VehicleDto> vehicles = new ArrayList<VehicleDto>();
	private VoucherService service = Factory.service.forVoucherService();
	private ClientDto sponsor = null;
	private VehicleDto vehicle = null;
	private List<ClientDto> clients = new ArrayList<>();
	private int numGeneratedVouchersSecondTime;
	private int numGeneratedVouchers = 0;

	public GenerateVouchersSteps(TestContext ctx) {
		this.ctx = ctx;
	}

	@Given("this client as sponsor")
	public void thisClientAsSponsor(DataTable data) {
		List<Map<String, String>> table = data.asMaps();
		Map<String, String> row = table.get(0);

		this.sponsor = new ClientUtil()
				.withDni(row.get("clientdni"))
				.register()
				.get();

		this.vehicle = new VehicleUtil()
				.withPlate(row.get("vehiclePlate"))
				.forOwner(sponsor.id)
				.register()
				.get();
		
		vehicles.add(vehicle);
	}

	@Given("the following relation of clients and vehicles")
	public void theFollowingClientsAndVehicles(DataTable data) {
		List<Map<String, String>> table = data.asMaps();
		
		for (Map<String, String> row : table) {
			ClientDto newClient = newClient( row.get("clientdni") );
			String plate = row.get("vehiclePlate");

			clients.add( newClient );
			vehicles.add( newVehicle(newClient, plate) );
		}
	}

	private VehicleDto newVehicle(ClientDto newClient, String string) {
		return new VehicleUtil()
				.withPlate(string)
				.forOwner(newClient.id)
				.register()
				.get();
	}

	private ClientDto newClient( String dni ) {
		return new ClientUtil()
				.withDni( dni )
				.registerIfNew()
				.get();
	}

	@Given("the following workorders")
	public void theFollowingWorkorders(DataTable data) {
		List<Map<String, String>> table = data.asMaps();
		List<WorkOrderDto> finishedWorkorders = new ArrayList<WorkOrderDto>();
		
		for (Map<String, String> row : table) {
			String vehicleId = findVehicleByPlate( row.get("platenum") );
			
			String status = row.get("status");
			String invoiceNumber = row.get("invoiceNumber");
			String invoiceId = status.equals("INVOICED")
								? findInvoiceIdByNumber( invoiceNumber )
								: null;

			WorkOrderDto dto = new WorkOrderUtil()
					.forVehicle( vehicleId )
					.withStatus( status )
					.withDate( row.get("date") )
					.withUsedForVoucher( row.get("usedForVoucher") )
					.withInvoice( invoiceId )
					.register()
					.get();

			if (status.equals("FINISHED")) {
				finishedWorkorders.add(dto);
			}
		}
		
		this.ctx.put(TestContext.Key.WORKORDERS, finishedWorkorders);
	}

	private String findInvoiceIdByNumber(String invoiceNumber) {
		@SuppressWarnings("unchecked")
		List<InvoiceDto> invoices = (List<InvoiceDto>) this.ctx
				.get(TestContext.Key.INVOICES);
		
		return invoices.stream()
				.filter(i -> i.number == Long.parseLong( invoiceNumber ))
				.findFirst()
				.get()
				.id;
	}

	private String findVehicleByPlate(String plate) {
		return new FindVehicleByPlateSqlUnitOfWork( plate )
				.execute()
				.get()
				.get().id;
	}

	@Given("the following recommendations")
	public void theFollowingRecommendations(DataTable data) {
		List<Map<String, String>> table = data.asMaps();
		FindClientByDniSqlUnitOfWork finder;
		String sponsorId, clientId;

		for (Map<String, String> row : table) {
			finder = new FindClientByDniSqlUnitOfWork(row.get("sponsordni"));
			finder.execute();
			sponsorId = finder.get().get().id;
			finder = new FindClientByDniSqlUnitOfWork(row.get("clientdni"));
			finder.execute();
			clientId = finder.get().get().id;
			new RecommendationUtil().fromSponsor(sponsorId).forClient(clientId)
					.withUse(row.get("usedForVoucher")).register();
		}

	}

	@Given("the following invoices")
	public void theFollowingInvoices(DataTable data) {
		List<Map<String, String>> table = data.asMaps();
		List<InvoiceDto> invoices = new ArrayList<>();

		// | number | status | date | used | money | vat |
		// | 1 | PAID | 2021-09-2 | false | 1500 | 21.0 |
		for (Map<String, String> row : table) {
			InvoiceDto newInvoice = new InvoiceUtil()
					.withAmount( Double.parseDouble(row.get("money")) )
					.withStatus( row.get("status") )
					.withUsedForVoucher( row.get("use") )
					.withNumber( Long.parseLong(row.get("number")) )
					.register()
					.get();
			
			invoices.add(newInvoice);
		}

		this.ctx.put(TestContext.Key.INVOICES, invoices);
	}
	
	@When("I generate vouchers")
	public void iGenerateVouchers() throws BusinessException {
		numGeneratedVouchers = service.generateVouchers();
	}

	@When("I generate vouchers again")
	public void iGenerateVouchersAgain() throws BusinessException {
		this.numGeneratedVouchersSecondTime = service.generateVouchers();
	}

	@Then("We get no vouchers")
	public void weGetNoVouchers() {
		assertTrue(this.numGeneratedVouchersSecondTime == 0);
	}

	@Then("We get the following vouchers")
	public void weGetTheFollowingVouchers(DataTable data) {
		List<Map<String, String>> list = data.asMaps();

		int expectednumber = countVouchers(list);
		assertEquals( expectednumber, numGeneratedVouchers );
		
		for (Map<String, String> row : list) {
			int expectedVouchers = Integer.parseInt( row.get("num") );
			String accumulateds = row.get("accumulated");
			String balances = row.get("balance");
			String descriptions = row.get("description");
			String clientid = getClientIdByDni( row.get("client") );
			
			List<VoucherDto> generated = new PaymentMeanUtil()
					.findVouchersByClientId(clientid);

			assertEquals(expectedVouchers, generated.size() );
			assertTrue( matchAccumulatedField(generated, accumulateds) );
			assertTrue( matchBalanceField(generated, balances) );
			assertTrue( matchDescriptions(generated, descriptions) );
		}

	}

	private boolean matchBalanceField(List<VoucherDto> vouchers,
			String balances) {
		List<Double> generated = selectBalanceFrom(vouchers);
		List<Double> expected = parseDoubles(balances);
		
		Collections.sort(expected);
		Collections.sort(generated);
		
		return expected.equals(generated);
	}

	private boolean matchDescriptions(List<VoucherDto> vouchers,
			String descriptions) {

		List<String> generated = selectDescriptionFrom(vouchers);
		List<String> expected = Arrays.asList( descriptions.split(",") );
		Collections.sort(expected);
		Collections.sort(generated);
		
		return expected.equals( generated );
	}

	private String getClientIdByDni(String dni) {
		return new ClientUtil().findByDni(dni).id;
	}

	private Integer countVouchers(List<Map<String, String>> list) {
		return list.stream()
				.map(row -> Integer.parseInt( row.get("num") ))
				.reduce(0, Integer::sum);
	}

	private List<String> selectDescriptionFrom(List<VoucherDto> vouchers) {
		return vouchers.stream()
				.map(r -> r.description)
				.collect( Collectors.toList() );
	}

	private boolean matchAccumulatedField(List<VoucherDto> vouchers,
			String accumulated) {
		List<Double> generated = selectAccumulatedFrom(vouchers);
		List<Double> expected = parseDoubles(accumulated);
		
		Collections.sort(expected);
		Collections.sort(generated);
		
		return expected.equals(generated);
	}

	private List<Double> parseDoubles(String doublesString) {
		String[] temp = doublesString.split(",");
		return Arrays.stream(temp) 		// stream of String
				.map(Double::valueOf) 	// stream of Integer
				.collect(Collectors.toList());
	}

	private List<Double> selectAccumulatedFrom(List<VoucherDto> vouchers) {
		return vouchers.stream()
				.map(r -> r.accumulated)
				.collect(Collectors.toList());
	}

	private List<Double> selectBalanceFrom(List<VoucherDto> vouchers) {
		return vouchers.stream()
				.map(item -> item.balance)
				.collect(Collectors.toList());
	}

}
