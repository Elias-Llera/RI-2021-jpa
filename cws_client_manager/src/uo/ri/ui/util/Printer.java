package uo.ri.ui.util;

import java.util.List;

import alb.util.console.Console;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingPaymentMeanDto;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.service.paymentmean.VoucherService.VoucherSummaryDto;
import uo.ri.cws.application.service.vehicletype.VehicleTypeCrudService.VehicleTypeDto;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;

public class Printer {

	public static void printInvoice(InvoiceDto invoice) {

		double importeConIVa = invoice.total;
		double iva =  invoice.vat;
		double importeSinIva = importeConIVa / (1 + iva / 100);

		Console.printf("Invoice #: %d\n", 				invoice.number );
		Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", 	invoice.date);
		Console.printf("\tTotal: %.2f �\n", 			importeSinIva);
		Console.printf("\tTax: %.1f %% \n", 			invoice.vat );
		Console.printf("\tTotal, tax inc.: %.2f �\n", 	invoice.total );
		Console.printf("\tStatus: %s\n", 				invoice.status );
	}

	public static void printPaymentMeans(List<InvoicingPaymentMeanDto> medios) {
		Console.println();
		Console.println("Available payment means");

		Console.printf("\t%s \t%-8.8s \t%s \n", "Id", "Type", "Acummulated");
		for (InvoicingPaymentMeanDto medio : medios) {
			printPaymentMean( medio );
		}
	}

	private static void printPaymentMean(InvoicingPaymentMeanDto medio) {
		Console.printf("\t%s \t%-8.8s \t%s \n"
				, medio.id
				, medio.getClass().getName()  // not the best...
				, medio.accumulated
		);
	}

	public static void printWorkOrder(WorkOrderDto rep) {

		Console.printf("\t%s \t%-40.40s \t%td/%<tm/%<tY \t%-12.12s \t%.2f\n"
				, rep.id
				, rep.description
				, rep.date
				, rep.status
				, rep.total
		);
	}

	public static void printMechanic(MechanicDto m) {

		Console.printf("\t%s %-10.10s %-15.15s %-25.25s\n"
				, m.id
				, m.dni
				, m.name
				, m.surname
			);
	}

	public static void printVehicleType(VehicleTypeDto vt) {

		Console.printf("\t%s %-10.10s %5.2f %d\n"
				, vt.id
				, vt.name
				, vt.pricePerHour
				, vt.minTrainigHours
			);
	}


	public static void printVoucherSummary(VoucherSummaryDto vs) {
		
		Console.printf("%10.10s \t%20.20s \t%20.20s \t%3d \t%.2f \t%.2f \t%.2f"
				, vs.dni
				, vs.name
				, vs.surname
				, vs.availableBalance
				, vs.totalAmount
				, vs.issued
				, vs.consumed
			);
	}
	
	public static void printVoucher(VoucherDto v) {
		Console.printf("%d \t%s \t%-20.20s \t%.2f \t%.2f"
				, v.id
				, v.code
				, v.description
				, v.accumulated
				, v.balance
			);
	}
}
