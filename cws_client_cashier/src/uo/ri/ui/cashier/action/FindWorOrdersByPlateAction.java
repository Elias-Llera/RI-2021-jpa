package uo.ri.ui.cashier.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.ui.util.Printer;

public class FindWorOrdersByPlateAction implements Action {

	@Override
	public void execute() throws Exception {
		InvoicingService cs = Factory.service.forCreateInvoiceService();

		String plate = Console.readString("Plate number");

		List<InvoicingWorkOrderDto> reps = cs.findWorkOrdersByPlateNumber( plate );

		if (reps.size() == 0) {
			Console.printf("There is not work orders for the vehicle\n");
			return;
		}

		Console.println("\nWork orders for the vehicle\n");
		for(InvoicingWorkOrderDto rep : reps) {
			Printer.printWorkOrder( rep );
		}
	}

}
