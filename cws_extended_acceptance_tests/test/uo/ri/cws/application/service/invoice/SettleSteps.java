package uo.ri.cws.application.service.invoice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.service.common.TestContext;
import uo.ri.cws.application.service.invoice.InvoicingService.ChargeDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.util.CardUtil;
import uo.ri.cws.application.service.util.ChargeUtil;
import uo.ri.cws.application.service.util.InvoiceUtil;
import uo.ri.cws.application.service.util.PaymentMeanUtil;
import uo.ri.cws.application.service.util.VoucherUtil;

public class SettleSteps {

	private TestContext ctx;
	private ClientDto theClient; // client registered
	private InvoiceDto theInvoice; // invoice registered

	private InvoicingService service = Factory.service.forCreateInvoiceService();
	private Map<String, Double> charges = new HashMap<>();

	@SuppressWarnings("unchecked")
	public SettleSteps(TestContext ctx) {
		this.ctx = ctx;
		theClient = (ClientDto) ctx.get(TestContext.Key.CLIENT);
		theInvoice = ((List<InvoiceDto>) ctx.get(TestContext.Key.INVOICES))
				.get(0);
	}

	@Given("the following charges for the invoice")
	public void theFollowingCharges(DataTable data) {
		// # | type | payident | amount |
		List<Map<String, String>> table = data.asMaps();
		for (Map<String, String> row : table) {
			String pmId = getPaymentId(row);
			Double amount = Double.parseDouble(row.get("amount"));
			charges.put(pmId, amount);
		}
	}

	@Given("the following credit cards")
	public void theFollowingCreditCards(DataTable data) {
		// | number | type | validthru |
		List<Map<String, String>> table = data.asMaps();

		for (Map<String, String> row : table) {
			new CardUtil()
					.forClient(theClient.id)
					.withNumber( row.get("number") )
					.withType( row.get("type") )
					.withValidDate( LocalDate.parse(row.get("validthru")) )
					.register();
		}
	}

	private String getPaymentId(Map<String, String> row) {
		switch (row.get("type")) {
		case "CASH": return new PaymentMeanUtil()
						.findCashByClientId(theClient.id)
						.get()
						.id;

		case "CREDITCARD": return new CardUtil()
						.findCardByNumber( row.get("payident") )
						.get()
						.id;

		case "VOUCHER": return new VoucherUtil()
						.findByCode( row.get("payident") )
						.get()
						.id;
		}
		return null;
	}

	@When("I settle the invoice")
	public void iSettleTheInvoice() throws BusinessException {
		service.settleInvoice(this.theInvoice.id, charges);
	}

	@When("I try to settle the invoice")
	public void iTryToSettleTheInvoice() {
		trySettleAndKeepException(this.theInvoice.id, charges);
	}

	private void trySettleAndKeepException(String id, Map<String, Double> charges) {
		try {
			service.settleInvoice(id, charges);
			fail();
		} catch (BusinessException ex) {
			ctx.setException(ex);
		} catch (IllegalArgumentException ex) {
			ctx.setException(ex);
		}
	}

	@Then("the invoice is PAID")
	public void theInvoiceIsPAID() {
		InvoiceDto invoice = new InvoiceUtil().findById(theInvoice.id).get();
		assertTrue(invoice.status.equals("PAID"));
	}

	@Then("payment means are updated")
	public void paymentMeansAreUpdated() {

	}

	@Then("the following charges are created")
	public void theFollowingChargesAreCreated(DataTable data) {
		// | paymentMean | invoiceNumber | amount |
		List<ChargeDto> chargesInDB = new ChargeUtil().findAll();

		List<Map<String, String>> table = data.asMaps();
		for (Map<String, String> row : table) {
			String invoiceId = getInvoiceIdFromDB(row);
			String paymentMeanId = getPaymentMeanIdFromDB(row);

			Optional<ChargeDto> maybe = findOne(chargesInDB, invoiceId, paymentMeanId);
			assertTrue(maybe.isPresent());
			
			double expected = Double.parseDouble(row.get("amount"));
			assertEquals( expected, maybe.get().amount, 0.01);
		}

	}

	private String getInvoiceIdFromDB(Map<String, String> row) {
		Long number = Long.parseLong( row.get("invoiceNumber") );
		String invoiceId = new InvoiceUtil() .findByNumber(number).get().id;
		return invoiceId;
	}

	private Optional<ChargeDto> findOne(List<ChargeDto> chargesInDB,
			String invoiceid, String paymentMeanId) {
		return chargesInDB.stream()
				.filter(ch -> ch.invoice_id.equals(invoiceid))
				.filter(ch -> ch.paymentMean_id.equals(paymentMeanId))
				.findFirst();
	}

	private String getPaymentMeanIdFromDB(Map<String, String> row) {
		String type = row.get("paymentMean");
		if (type.equals("CASH")) {
			PaymentMeanUtil util = new PaymentMeanUtil();
			return util.findCashByClientId(theClient.id).get().id;
		}
		if (type.equals("CREDITCARD")) {
			String number = row.get("payident");
			return new CardUtil().findCardByNumber(number).get().id;
		}

		if (type.equals("VOUCHER")) {
			String code = row.get("payident");
			return new VoucherUtil().findByCode(code).get().id;
		}

		return null;
	}

	@Given("the following vouchers")
	public void theFollowingVouchers(DataTable data) {
		// | code | money | description |
		List<Map<String, String>> table = data.asMaps();

		for (Map<String, String> row : table) {
			new VoucherUtil()
					.withCode( row.get("code") )
					.withBalance( Double.parseDouble( row.get("money") ))
					.withDescription( row.get("description") )
					.forClient(theClient.id)
					.register()
					.get();
		}

	}

}
