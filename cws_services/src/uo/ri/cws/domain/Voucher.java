package uo.ri.cws.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVouchers")
public class Voucher extends PaymentMean {
    @Column(unique = true)
    private String code;
    private double available = 0.0;
    private String description;

    Voucher() {
    }

    public Voucher(String code) {
	ArgumentChecks.isNotEmpty(code);
	this.code = code;
    }

    public Voucher(String code, String description, double available) {
	this(code);
	this.description = description;
	ArgumentChecks.isTrue(available >= 0);
	this.available = available;
    }

    public Voucher(String code, double available) {
	this(code);
	ArgumentChecks.isTrue(available >= 0);
	this.available = available;
	this.description = "no-description";
    }

    public Voucher(String code, String description, double available,
	    Client client) {
	this(code, description, available);
	ArgumentChecks.isNotNull(client);
	Associations.Pay.link(client, this);
    }

    /**
     * Augments the accumulated (super.pay(amount) ) and decrements the
     * available
     * 
     * @throws IllegalStateException if not enough available to pay
     */
    @Override
    public void pay(double amount) {
	if (available < amount) {
	    throw new IllegalStateException(
		    "Not enough money available in the voucher.");
	}
	super.pay(amount);
	available -= amount;
    }

    public String getDescription() {
	return description;
    }

    public String getCode() {
	return code;
    }

    public double getAvailable() {
	return available;
    }

    @Override
    public String toString() {
	return "Voucher [code=" + code + ", available=" + available
		+ ", description=" + description + ", getAccumulated()="
		+ getAccumulated() + "]";
    }

    @Override
    public boolean canPay(double amount) {
	return available >= amount;
    }

}
