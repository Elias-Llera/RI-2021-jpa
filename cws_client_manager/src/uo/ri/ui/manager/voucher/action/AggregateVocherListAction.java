package uo.ri.ui.manager.voucher.action;

import java.util.List;

import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.paymentmean.VoucherService;
import uo.ri.cws.application.service.paymentmean.VoucherService.VoucherSummaryDto;
import uo.ri.ui.util.Printer;

public class AggregateVocherListAction implements Action {

	@Override
	public void execute() throws Exception {
		VoucherService s = Factory.service.forVoucherService();
		
		List<VoucherSummaryDto> summaries = s.getVoucherSummary();
	
		for(VoucherSummaryDto vs: summaries) {
			Printer.printVoucherSummary( vs );
		}
	}

}
