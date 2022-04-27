package uo.ri.cws.application.service.paymentmean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.service.common.TestContext;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CardDto;
import uo.ri.cws.application.service.util.CardUtil;

public class AddCreditCardSteps {

	private TestContext ctx;

	private CardDto card;
	private CardDto faultyCard;
	private ClientDto client;

	private PaymentMeanCrudService service = Factory.service
			.forPaymentMeanService();

	public AddCreditCardSteps(TestContext ctx) {
		this.ctx = ctx;
		client = (ClientDto) ctx.get(TestContext.Key.CLIENT);
	}

	@Given("a credit card")
	public void aCreditCard() {
		card = new CardUtil().get();
	}

	@When("I add the credit card")
	public void iAddTheCreditCard() throws BusinessException {
		card.clientId = client.id;
		service.addCard(card);

	}

	@When("I try to add the credit card to a non existent client")
	public void iTryToAddTheCreditCardToANonExistentClient()
			throws BusinessException {
		card.clientId = UUID.randomUUID().toString();
		tryAddAndKeepException(card);
	}

	@Then("the credit card results added to the system")
	public void theCreditCardResultsAddedToTheSystem()
			throws BusinessException {

		Optional<CardDto> oc = new CardUtil().findCardByNumber(card.cardNumber);
		assertTrue(oc.isPresent());

		CardDto cardFound = oc.get();		
		assertEquals( cardFound.clientId, card.clientId );
		assertEquals( cardFound.cardExpiration, card.cardExpiration );
		assertEquals( cardFound.cardNumber, card.cardNumber );
		assertEquals( cardFound.accumulated, card.accumulated, 0.001);
	}

	@When("I try to add another credit card with the same number")
	public void iTryToAddARepeatedCreditCardToTheClient() {
		faultyCard = new CardUtil().get();
		faultyCard.cardNumber = card.cardNumber;
		faultyCard.clientId = card.clientId;

		tryAddAndKeepException(faultyCard);
	}

	@When("I try to add the credit card to the client")
	public void iTryToAddTheCreditCardToTheClient() {
		card.clientId = client.id;
		tryAddAndKeepException(card);

	}

	@Given("an outdated credit card")
	public void anOutdatedCreditCard() {
		card = new CardUtil().get();
		card.cardExpiration = LocalDate.parse("2018-12-27");
	}

	@When("I try to add a new credit card with null argument")
	public void iTryToAddANewCreditCardWithNullArgument() {
		tryAddAndKeepException(null);

	}

	@When("I try to add a new credit card with null id")
	public void iTryToAddANewCreditCardWithNullId() {
		card = new CardUtil().get();
		card.clientId = client.id;
		card.id = null;

		tryAddAndKeepException(card);

	}

	@When("I try to add a new credit card with null number")
	public void iTryToAddANewCreditCardWithNullNumber() {
		card = new CardUtil().get();
		card.clientId = client.id;
		card.cardNumber = null;

		tryAddAndKeepException(card);
	}

	@When("I try to add a new credit card with null type")
	public void iTryToAddANewCreditCardWithNullType() {
		card = new CardUtil().get();
		card.clientId = client.id;
		card.cardType = null;

		tryAddAndKeepException(card);
	}

	@When("I try to add a new credit card with null client id")
	public void iTryToAddANewCreditCardWithNullClientId() {
		card = new CardUtil().get();
		card.clientId = null;

		tryAddAndKeepException(card);
	}

	@When("I try to add a new credit card with null expiration date")
	public void iTryToAddANewCreditCardWithNullExpirationDate() {
		card = new CardUtil().get();
		card.clientId = client.id;
		card.cardExpiration = null;

		tryAddAndKeepException(card);
	}

	@When("I try to add a new credit card with {string}, {string}, {string}, {string}")
	public void iTryToAddANewCreditCardWithClientid(String id, String number,
			String type, String clientId) {
		card = new CardUtil().get();
		card.id = id;
		card.cardNumber = number;
		card.cardType = type;
		card.clientId = clientId;

		tryAddAndKeepException(card);
	}

	private void tryAddAndKeepException(CardDto dto) {
		try {
			service.addCard(dto);
			fail();
		} catch (BusinessException ex) {
			ctx.setException(ex);
		} catch (IllegalArgumentException ex) {
			ctx.setException(ex);
		}

	}

}
