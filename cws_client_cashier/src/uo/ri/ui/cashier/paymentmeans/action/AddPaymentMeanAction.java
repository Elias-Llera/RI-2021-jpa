package uo.ri.ui.cashier.paymentmeans.action;

import java.time.LocalDate;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CardDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;

public class AddPaymentMeanAction implements Action {

	@Override
	public void execute() throws Exception {
		PaymentMeanCrudService service = Factory.service.forPaymentMeanService();

		String clientId = Console.readString("Client id");
		int type = Console.readInt("Payment mean type (1 - Card, 2 - Voucher)");
		if (type == 1) {
			CardDto card = askForCard(clientId);
			card = service.addCard(card);
			Console.println("Card added with id: " + card.id);
		} else if (type == 2) {
			VoucherDto voucher = askForVoucher(clientId);
			voucher = service.addVoucher(voucher);
			Console.println("Voucher added with id: " + voucher.id);
		} else {
			throw new BusinessException("Invalid payment mean type");
		}

		Console.println("Medio de pago añadido");
	}

	private VoucherDto askForVoucher(String clientId) {
		VoucherDto res = new VoucherDto();
		res.description = Console.readString("Voucher description");
		res.balance = Console.readDouble("Voucher amount");
		return res;
	}

	private CardDto askForCard(String clientId) {
		CardDto res = new CardDto();
		res.cardType = Console.readString("Card type (VISA, MASTER, etc.)");
		res.cardNumber = Console.readString("Card number");
		int month = Console.readInt("Valid until, month (1..12)");
		int year = Console.readInt("Valid until, year");
		res.cardExpiration = LocalDate.of(year, month, 31);
		return res;
	}

}