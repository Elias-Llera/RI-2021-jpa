package uo.ri.cws.application.service.paymentmean;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.service.common.TestContext;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CardDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CashDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.PaymentMeanDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;

public class FindByClientIdSteps {

	private TestContext ctx;

	private PaymentMeanCrudService service = Factory.service
			.forPaymentMeanService();
	// private MechanicDto mechanic, found;
	// private List<MechanicDto> mechanics;
	//
	private String clientID = null;

	private List<PaymentMeanDto> found;

	public FindByClientIdSteps(TestContext ctx) {
		this.ctx = ctx;
	}

	@When("I look for payment mean by client id")
	public void iLookForPaymentMeanByClientId() throws BusinessException {
		clientID = ((ClientDto) ctx.get(TestContext.Key.CLIENT)).id;

		found = service.findPaymentMeansByClientId(clientID);
	}

	@Then("I get the cash")
	public void iGetTheCash() {
		List<PaymentMeanDto> aux = found.stream()
				.filter(pm -> pm instanceof CashDto)
				.collect(Collectors.toList());
		assertTrue(aux.size() == 1);
		assertTrue(aux.get(0) instanceof CashDto);
		assertTrue(aux.get(0).clientId.equals(clientID));
	}

	@Then("I get credit cards registered")
	public void iGetCreditCardsRegistered() {

		List<PaymentMeanDto> ps = found.stream()
				.filter(pm -> pm instanceof CardDto)
				.collect(Collectors.toList());

		@SuppressWarnings("unchecked")
		List<PaymentMeanDto> saved = (List<PaymentMeanDto>) ctx
				.getResultList();
		List<PaymentMeanDto> cardSaved = saved.stream()
				.filter(pm -> pm instanceof CardDto)
				.collect(Collectors.toList());

		assertTrue(cardSaved.size() == ps.size());
		for (PaymentMeanDto c : cardSaved) {
			Optional<PaymentMeanDto> aux = ps.stream()
					.filter(p -> p.id.equals(c.id)).findFirst();
			assertTrue(aux.isPresent());
		}

	}

	@Then("I get vouchers registered")
	public void iGetVouchersRegistered() {
		List<PaymentMeanDto> ps = found.stream()
				.filter(pm -> pm instanceof VoucherDto)
				.collect(Collectors.toList());

		@SuppressWarnings("unchecked")
		List<PaymentMeanDto> saved = (List<PaymentMeanDto>) ctx
				.getResultList();
		List<PaymentMeanDto> voucherSaved = saved.stream()
				.filter(pm -> pm instanceof VoucherDto)
				.collect(Collectors.toList());

		assertTrue(voucherSaved.size() == ps.size());
		for (PaymentMeanDto c : voucherSaved) {
			Optional<PaymentMeanDto> aux = ps.stream()
					.filter(p -> p.id.equals(c.id)).findFirst();
			assertTrue(aux.isPresent());
		}
	}

	@When("I look payment means for a non existing client id")
	public void iLookPaymentMeansForANonExistingClientId()
			throws BusinessException {

		String aux = UUID.randomUUID().toString();
		found = service.findPaymentMeansByClientId(aux);

	}

	@Then("payment means not found")
	public void paymentMeansNotFound() {
		assertTrue(found.isEmpty());
	}

	@When("I try to find payment means with null client id")
	public void iTryToFindPaymentMeansWithNullClientId() {
		tryFindAndKeepException(null);
	}

	@When("I look for payment means  by wrong client id {string}")
	public void iLookForPaymentMeansByWrongClientId(String id) {
		tryFindAndKeepException(id);

	}

	private void tryFindAndKeepException(String id) {
		try {
			service.findPaymentMeansByClientId(id);
			fail();
		} catch (BusinessException ex) {
			ctx.setException(ex);
		} catch (IllegalArgumentException ex) {
			ctx.setException(ex);
		}
	}

}
