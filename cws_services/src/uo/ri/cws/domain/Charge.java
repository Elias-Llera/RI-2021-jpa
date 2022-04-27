package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.Invoice.InvoiceStatus;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TCharges", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "INVOICE_ID", "PAYMENTMEAN_ID" }) })
public class Charge extends BaseEntity {
	// natural attributes
	private double amount = 0.0;

	// accidental attributes
	@ManyToOne
	private Invoice invoice;
	@ManyToOne
	private PaymentMean paymentMean;

	public Charge() {
	}

	public Charge(Invoice invoice, PaymentMean paymentMean) {
		ArgumentChecks.isNotNull(paymentMean);
		ArgumentChecks.isNotNull(invoice);

		Associations.Charges.link(paymentMean, this, invoice);
	}

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		this(invoice, paymentMean);

		ArgumentChecks.isTrue(amount >= 0);

		this.amount = amount;

		paymentMean.pay(amount);

		// FINISH

		// store the amount
		// increment the paymentMean accumulated -> paymentMean.pay( amount )
		// link invoice, this and paymentMean
	}

	/**
	 * Unlinks this charge and restores the accumulated to the payment mean
	 * 
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		if (invoice.getStatus().equals(InvoiceStatus.PAID)) {
			throw new IllegalStateException("The invoice is already sttled.");
		}
		paymentMean.pay(-amount);
		Associations.Charges.unlink(this);
		// asserts the invoice is not in PAID status
		// decrements the payment mean accumulated ( paymentMean.pay( -amount) )
		// unlinks invoice, this and paymentMean
	}

	public double getAmount() {
		return amount;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

	void _setAmount(double amount) {
		this.amount = amount;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	void _setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}

	@Override
	public String toString() {
		return "Charge [amount=" + amount + ", invoice=" + invoice
				+ ", paymentMean=" + paymentMean + "]";
	}

}
