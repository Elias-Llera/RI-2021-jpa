package uo.ri.cws.application.service.paymentmean.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CardDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.CreditCard;

public class AddCard implements Command<CardDto>{

	private CardDto card;
	PaymentMeanRepository paymentMeanRepository = Factory.repository.forPaymentMean();
	ClientRepository clientRepository = Factory.repository.forClient();
	
	public AddCard(CardDto card) {
		ArgumentChecks.isNotNull(card);
		ArgumentChecks.isNotNull(card.cardExpiration);
		ArgumentChecks.isNotEmpty(card.cardNumber);
		ArgumentChecks.isNotEmpty(card.cardType);
		ArgumentChecks.isNotEmpty(card.clientId);
		this.card = card;
	}

	@Override
	public CardDto execute() throws BusinessException {
		Optional<Client> oClient = clientRepository.findById(card.clientId);
		BusinessChecks.exists(oClient);
		BusinessChecks.isTrue(paymentMeanRepository.findCreditCardByNumber(card.cardNumber).isEmpty(), 
				"There's already a card with this number.");
		
		CreditCard newCard = new CreditCard(card.cardNumber, card.cardType, card.cardExpiration, oClient.get());
		paymentMeanRepository.add(newCard);
		
		card.id = newCard.getId();
		return card;
	}

}
