package uo.ri.cws.application.service.paymentmean;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.service.common.TestContext;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CardDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CashDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.PaymentMeanDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.service.util.CardUtil;
import uo.ri.cws.application.service.util.ChargeUtil;
import uo.ri.cws.application.service.util.PaymentMeanUtil;
// import uo.ri.cws.application.service.util.ClientUtil.ClientDto;
import uo.ri.cws.application.service.util.VoucherUtil;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;

public class DeletePaymentMeanSteps {

	private TestContext ctx;

	private CardDto card;
	private VoucherDto voucher;
	private InvoiceDto invoice = null;

	private PaymentMeanCrudService service = Factory.service.forPaymentMeanService();

	public DeletePaymentMeanSteps(TestContext ctx) {
		this.ctx = ctx;
	}

	@Given("a credit card registered")
	public void aClientWithARegisteredCreditCard() throws BusinessException {
		ClientDto client = (ClientDto) ctx.get(TestContext.Key.CLIENT);
		card = new CardUtil().forClient(client.id).register().get();
		ctx.put(TestContext.Key.PAYMENTMEAN, card);

		@SuppressWarnings("unchecked")
		List<PaymentMeanDto> aux = (List<PaymentMeanDto>) ctx.getResultList();
		aux.add(card);
	}

	@Given("a voucher registered")
	public void aVoucherRegistered() throws BusinessException {
		ClientDto client = (ClientDto) ctx.get(TestContext.Key.CLIENT);
		voucher = new VoucherUtil().forClient(client.id).register().get();

		ctx.put(TestContext.Key.PAYMENTMEAN, voucher);

		@SuppressWarnings("unchecked")
		List<PaymentMeanDto> aux = (List<PaymentMeanDto>) ctx.getResultList();
		aux.add(voucher);
	}

	@When("I try to remove the cash payment mean")
	public void iTryToRemoveTheCashPaymentMean() throws BusinessException {
		ClientDto client = (ClientDto) ctx.get(TestContext.Key.CLIENT);
		CashDto cash = new PaymentMeanUtil().findCashByClientId(client.id).get();
		
		tryDeleteAndKeepException( cash.id );
	}

	@When("I remove the credit card")
	public void iRemoveTheCreditCard() throws BusinessException {
		service.deletePaymentMean(card.id);
	}

	@When("I try to remove a non existent payment mean")
	public void iTryToRemoveANonExistentPaymentMean() throws BusinessException {
		tryDeleteAndKeepException(UUID.randomUUID().toString());
	}

	@When("I try to delete a null payment mean")
	public void iTryToDeleteANullPaymentMean() {
		tryDeleteAndKeepException(null);
	}

	@Given("an invoice")
	public void anInvoice() throws BusinessException {
		InvoicingService invoiceService = Factory.service.forCreateInvoiceService();
		List<String> workOrderIds = extractListOfWorkOrderIds();
		invoice = invoiceService.createInvoiceFor(workOrderIds);
	}

	private List<String> extractListOfWorkOrderIds() {
		@SuppressWarnings("unchecked")
		List<WorkOrderDto> list = (List<WorkOrderDto>) ctx
				.get(TestContext.Key.WORKORDERS);
		
		return list.stream()
				.map(wo -> wo.id)
				.collect( Collectors.toList() );
	}

	@Given("some charges to the credit card")
	public void someChargesToCard() {
		new ChargeUtil()
				.forInvoice(invoice.id)
				.forPaymentMean(card.id)
				.register();
	}

	@Given("some charges to the voucher")
	public void someChargesToVoucher() {
		new ChargeUtil()
				.forInvoice(invoice.id)
				.forPaymentMean(voucher.id)
				.register();
	}

	@When("I try to remove the card")
	public void iTryToRemoveTheCard() {
		tryDeleteAndKeepException(card.id);
	}

	@When("I try to remove the voucher")
	public void iTryToRemoveTheVoucher() {
		tryDeleteAndKeepException(voucher.id);
	}

	@Then("the credit card no longer exists")
	public void theCreditCardNoLongerExists() throws BusinessException {
		assertTrue( service.findById(card.id).isEmpty() );
	}

	private void tryDeleteAndKeepException(String id) {
		try {
			service.deletePaymentMean(id);
			fail();
		} catch (BusinessException |  IllegalArgumentException ex) {
			ctx.setException(ex);
		}
	}

}
