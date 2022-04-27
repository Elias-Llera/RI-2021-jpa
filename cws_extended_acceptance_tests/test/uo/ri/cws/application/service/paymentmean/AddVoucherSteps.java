package uo.ri.cws.application.service.paymentmean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Optional;
import java.util.UUID;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.service.common.TestContext;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.service.util.VoucherUtil;

public class AddVoucherSteps {

	private TestContext ctx;

	private VoucherDto voucher;

	private PaymentMeanCrudService service = Factory.service
			.forPaymentMeanService();

	public AddVoucherSteps(TestContext ctx) {
		this.ctx = ctx;
	}

	@Given("a voucher")
	public void aVoucherRegistered() throws BusinessException {
		voucher = new VoucherUtil().get();
	}

	@When("I add the voucher")
	public void iAddTheVoucher() throws BusinessException {
		ClientDto client = (ClientDto) ctx.get(TestContext.Key.CLIENT);
		voucher.clientId = client.id;

		voucher = service.addVoucher(voucher);
	}

	@When("I try to add the voucher to a non existent client")
	public void iTryToAddTheVoucherToANonExistentClient()
			throws BusinessException {
		voucher.clientId = UUID.randomUUID().toString();

		tryAddAndKeepException(voucher);
	}

	@Then("the voucher results added to the system")
	public void theVoucherResultsAddedToTheSystem() throws BusinessException {

		Optional<VoucherDto> found = new VoucherUtil().findById(voucher.id);

		assertTrue( found.isPresent() );
		VoucherDto dto = found.get();
		
		assertEquals( voucher.clientId, dto.clientId);
		assertEquals( voucher.description, dto.description);
		assertEquals( voucher.balance, dto.balance, 0.001);
		assertEquals( voucher.accumulated, dto.accumulated, 0.001);
	}

	@When("I try to add another voucher with the same code")
	public void iTryToAddARepeatedCreditCardToTheClient() {
		VoucherDto faulty = new VoucherUtil().get();
		faulty.code = voucher.code;
		faulty.clientId = voucher.clientId;

		tryAddAndKeepException(faulty);
	}

	@When("I try to add a new voucher with null argument")
	public void iTryToAddANewVoucherWithNullArgument() {
		tryAddAndKeepException(null);

	}

	@When("I try to add a new voucher with null id")
	public void iTryToAddANewVoucherWithNullId() {
		ClientDto client = (ClientDto) ctx.get(TestContext.Key.CLIENT);
		voucher = new VoucherUtil().get();
		voucher.clientId = client.id;
		voucher.id = null;

		tryAddAndKeepException(voucher);
	}

	@When("I try to add a new voucher with null code")
	public void iTryToAddANewVoucherWithNullNumber() {
		ClientDto client = (ClientDto) ctx.get(TestContext.Key.CLIENT);
		voucher = new VoucherUtil().get();
		voucher.clientId = client.id;
		voucher.code = null;

		tryAddAndKeepException(voucher);
	}

	@When("I try to add a new voucher with null client id")
	public void iTryToAddANewVoucherWithNullClientId() {
		voucher = new VoucherUtil().get();
		voucher.clientId = null;

		tryAddAndKeepException(voucher);
	}

	@When("I try to add a new voucher with {string}, {string}, {string}, {string}, {double}")
	public void iTryToAddANewVoucherWith(String id, String code,
			String description, String clientId, double balance) {

		voucher = new VoucherUtil()
					.withId( id )
					.withCode( code )
					.withDescription( description)
					.forClient( clientId )
					.withBalance( balance)
					.get();

		tryAddAndKeepException(voucher);
	}

	private void tryAddAndKeepException(VoucherDto dto) {
		try {
			service.addVoucher(voucher);
			fail();
		} catch (BusinessException | IllegalArgumentException ex) {
			ctx.setException(ex);
		}
	}

}
