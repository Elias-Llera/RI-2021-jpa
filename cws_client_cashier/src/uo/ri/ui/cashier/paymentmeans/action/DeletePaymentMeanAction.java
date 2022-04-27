package uo.ri.ui.cashier.paymentmeans.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService;

public class DeletePaymentMeanAction implements Action {

	@Override
	public void execute() throws Exception {
		String id = Console.readString("Payment mean id");
		
		PaymentMeanCrudService service = Factory.service.forPaymentMeanService();
		service.deletePaymentMean( id );
		
		Console.println("The payment mean has been removed");
	}

}
