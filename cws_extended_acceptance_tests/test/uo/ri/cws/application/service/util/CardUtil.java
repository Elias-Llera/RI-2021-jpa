package uo.ri.cws.application.service.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CardDto;
import uo.ri.cws.application.service.util.sql.AddCreditCardSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindCardByNumberSqlUnitOfWork;

public class CardUtil {

	private CardDto dto = createDefaultCard();

	public CardDto get() {
		return dto;
	}

	private CardDto createDefaultCard() {
		CardDto res = new CardDto();
		res.id = UUID.randomUUID().toString();
		res.version = 1L;
		res.accumulated = 0.0;
		res.cardNumber = UUID.randomUUID().toString();
		res.cardType = "VISA";
		res.cardExpiration = LocalDate.now().plus(1, ChronoUnit.YEARS);
		return res;
	}

	public Optional<CardDto> findCardByNumber(String cardNumber) {
		return new FindCardByNumberSqlUnitOfWork(cardNumber).execute().get();
	}

	public CardUtil forClient(String id) {
		dto.clientId = id;
		return this;
	}
	
	public CardUtil withType(String type) {
		dto.cardType = type;
		return this;
	}

	public CardUtil withNumber(String number) {
		dto.cardNumber = number;
		return this;
	}

	public CardUtil withValidDate(LocalDate date) {
		dto.cardExpiration = date;
		return this;
	}

	public CardUtil register() {
		new AddCreditCardSqlUnitOfWork(dto).execute();
		return this;
	}


}
