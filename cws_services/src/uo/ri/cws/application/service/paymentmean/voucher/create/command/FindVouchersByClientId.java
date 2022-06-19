package uo.ri.cws.application.service.paymentmean.voucher.create.command;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindVouchersByClientId implements Command<List<VoucherDto>> {

    private String clientId;
    private PaymentMeanRepository paymentMeanRepository = Factory.repository
	    .forPaymentMean();

    public FindVouchersByClientId(String clientId) {
	ArgumentChecks.isNotEmpty(clientId, "The id is not valid.");
	this.clientId = clientId;
    }

    @Override
    public List<VoucherDto> execute() throws BusinessException {
	return DtoAssembler.toVoucherDtoList(
		paymentMeanRepository.findVouchersByClientId(clientId));
    }

}
