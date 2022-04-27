package uo.ri.cws.application.service.invoice.create.command;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ChargeRepository;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Charge;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.PaymentMean;

public class SettleInvoice implements Command<Void>{
	
	private String invoiceId;
	private Map<String, Double> charges;
	private InvoiceRepository invoiceRepository = Factory.repository.forInvoice();
	private PaymentMeanRepository paymentMeanRepository = Factory.repository.forPaymentMean();
	private ChargeRepository chargesRepository = Factory.repository.forCharge();

	public SettleInvoice(String invoiceId, Map<String, Double> charges) {
		ArgumentChecks.isNotEmpty(invoiceId, "The invoiceId is mandatory.");
		ArgumentChecks.isNotNull(charges, "The charges cannot be null.");
		this.invoiceId = invoiceId;
		this.charges = charges;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<Invoice> oInvoice = invoiceRepository.findById(invoiceId);
		List<PaymentMean> paymentMeans= checkCanSettle(oInvoice);
		
		for (PaymentMean pm : paymentMeans) {
			chargesRepository.add(new Charge(oInvoice.get(), pm));
		}
		
		oInvoice.get().settle();
		
		return null;
	}

	private List<PaymentMean> checkCanSettle(Optional<Invoice> oInvoice) throws BusinessException {
		List<PaymentMean> paymentMeans = new LinkedList<>();
		
		BusinessChecks.exists(oInvoice);
		BusinessChecks.isTrue(oInvoice.get().isNotSettled());
		
		double totalAmountInCharges = 0;
		double amountToPayForCharge = 0;
		for(String paymentMeanId : charges.keySet()) {
			Optional<PaymentMean> oPaymentMean = paymentMeanRepository.findById(paymentMeanId);
			BusinessChecks.exists(oPaymentMean);
			amountToPayForCharge = charges.get(paymentMeanId);
			BusinessChecks.isTrue(oPaymentMean.get().canPay(amountToPayForCharge));
			paymentMeans.add(oPaymentMean.get());
			totalAmountInCharges += amountToPayForCharge; 
		}
		BusinessChecks.isTrue(
				(Math.abs(oInvoice.get().computeTotal()-totalAmountInCharges) <= 0.01 ));
		
		return paymentMeans;
	}

}
