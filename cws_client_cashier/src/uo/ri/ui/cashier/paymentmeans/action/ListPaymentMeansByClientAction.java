package uo.ri.ui.cashier.paymentmeans.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.PaymentMeanDto;
import uo.ri.ui.util.Printer;

public class ListPaymentMeansByClientAction implements Action {

	@Override
	public void execute() throws Exception {
		String id = Console.readString("Cliente id");
		
		PaymentMeanCrudService service = Factory.service.forPaymentMeanService();
		List<PaymentMeanDto> medios = service.findPaymentMeansByClientId( id );
		Printer.printPaymentMeans(medios);
	}

}
