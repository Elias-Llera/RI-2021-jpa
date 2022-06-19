package uo.ri.cws.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCreditCards")
public class CreditCard extends PaymentMean {
    @Column(unique = true)
    private String number;
    private String type;
    private LocalDate validThru;

    CreditCard() {
    }

    public CreditCard(String number) {
	ArgumentChecks.isNotEmpty(number);
	this.number = number;
	LocalDate tomorrow = LocalDate.now().plusDays(1);
	this.validThru = tomorrow;
	this.type = "UNKNOWN";
    }

    public CreditCard(String number, String type, LocalDate validThru) {
	this(number);
	this.type = type;
	this.validThru = validThru;
    }

    public CreditCard(String cardNumber, String cardType,
	    LocalDate cardExpiration, Client client) {
	this(cardNumber, cardType, cardExpiration);
	Associations.Pay.link(client, this);
    }

    public String getNumber() {
	return number;
    }

    public String getType() {
	return type;
    }

    public LocalDate getValidThru() {
	return validThru;
    }

    @Override
    public void pay(double amount) {
	if (validThru.isBefore(LocalDate.now())) {
	    throw new IllegalStateException("The credit card has expired.");
	}
	super.pay(amount);
    }

    @Override
    public String toString() {
	return "CreditCard [number=" + number + ", type=" + type
		+ ", validThru=" + validThru + "]";
    }

    public boolean isValidNow() {
	return validThru.isAfter(LocalDate.now());
    }

    public void setValidThru(LocalDate validThru) {
	this.validThru = validThru;
    }

    @Override
    public boolean canPay(double amount) {
	return isValidNow();
    }

}
