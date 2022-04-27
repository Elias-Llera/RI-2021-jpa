package uo.ri.ui.cashier.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingPaymentMeanDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.ui.util.Printer;

public class SettleInvoiceAction implements Action {

	private InvoicingService cs = Factory.service.forCreateInvoiceService();

	@Override
	public void execute() throws Exception {
		Long number = Console.readLong("Invoice number?");
		Optional<InvoiceDto> oi = cs.findInvoiceByNumber(number);
		BusinessChecks.exists( oi, "There is no such invoice");
		InvoiceDto invoice = oi.get();

		Printer.printInvoice( invoice );

		String dni = Console.readString("Client dni?");
		List<InvoicingPaymentMeanDto> means = cs.findPayMeansByClientDni(dni);
		BusinessChecks.isFalse( means.isEmpty(),
				"The client has no payment means or the dni is wrong"
			);

		Map<String, Double> charges = askForCharges(means, invoice.total);

		cs.settleInvoice(invoice.id, charges);

		Console.println("The invoice has been settled");
	}

	private Map<String, Double> askForCharges(List<InvoicingPaymentMeanDto> means,
			double totalAmount) {
		Map<String, Double> res = new HashMap<>();

		do {
			showPaymentMeans(means);
			String id = askForPaymentMeanId();
			Double amount = askForAmount();
			res.put(id, amount);

		} while ( sumAmounts( res.values() ) < totalAmount );

		return res;
	}

	private double sumAmounts(Collection<Double> amounts) {
		return amounts.stream().mapToDouble(a -> a).sum();
	}

	private Double askForAmount() {
		return Console.readDouble("Amount to charge?");
	}

	private String askForPaymentMeanId() {
		return Console.readString("Payment mean id?");
	}

	private void showPaymentMeans(List<InvoicingPaymentMeanDto> means) {
		Printer.printInvoicingPaymentMeans( means );
	}

}
