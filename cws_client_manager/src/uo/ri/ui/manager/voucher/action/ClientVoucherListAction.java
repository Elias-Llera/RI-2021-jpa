package uo.ri.ui.manager.voucher.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.service.paymentmean.VoucherService;
import uo.ri.ui.util.Printer;

public class ClientVoucherListAction implements Action {

	class VoucherSummary {
		int count;
		double totalValue;
		double usedValue;
		double availableValue;
	}

	@Override
	public void execute() throws Exception {
		String id = Console.readString("Client id");
		
		VoucherService s = Factory.service.forVoucherService();
		
		List<VoucherDto> bonos = s.findVouchersByClientId( id );
		VoucherSummary summary = new VoucherSummary();
		for(VoucherDto v: bonos) {
			Printer.printVoucher( v );
			
			updateSummary( summary, v );
		}
		printVoucherSummary( summary );
	}

	private void printVoucherSummary(VoucherSummary summary) {
		Console.printf("\t%d %.2f %.2f %.2f\n",  
				summary.count
				, summary.totalValue
				, summary.usedValue
				, summary.availableValue
			);
	}

	private void updateSummary(VoucherSummary summary, VoucherDto v) {
		summary.count++;
		summary.totalValue += v.accumulated + v.balance;
		summary.usedValue += v.accumulated;
		summary.availableValue += v.balance;
	}

}
