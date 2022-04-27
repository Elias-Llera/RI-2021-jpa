package uo.ri.ui.cashier.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.ui.util.Printer;

public class FindNotInvoicedWorkOrders implements Action {

	@Override
	public void execute() throws BusinessException {
		InvoicingService cs = Factory.service.forCreateInvoiceService();

		String dni = Console.readString("Client dni");

		Console.println("\nInvoice-pending work orders\n");

		List<InvoicingWorkOrderDto> reps = cs.findNotInvoicedWorkOrdersByClientDni( dni );

		if (reps.size() == 0) {
			Console.printf("There is no pending work orders\n");
			return;
		}

		for(InvoicingWorkOrderDto rep : reps) {
			Printer.printWorkOrder( rep );
		}
	}

}
