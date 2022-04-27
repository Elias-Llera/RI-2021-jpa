package uo.ri.ui.manager.voucher.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.paymentmean.VoucherService;

public class GenerateVoucherAction implements Action {

	@Override
	public void execute() throws Exception {
		
		VoucherService s = Factory.service.forVoucherService();
		
		int qty = s.generateVouchers();
		
		Console.printf("Generated vouchers: %d", qty);
	}
	

}
