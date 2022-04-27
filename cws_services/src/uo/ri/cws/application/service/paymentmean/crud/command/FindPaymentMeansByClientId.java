package uo.ri.cws.application.service.paymentmean.crud.command;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.PaymentMeanDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindPaymentMeansByClientId implements Command<List<PaymentMeanDto>>{

	private String clientId;
	PaymentMeanRepository paymentMeanRepository = Factory.repository.forPaymentMean();
	
	public FindPaymentMeansByClientId(String id) {
		ArgumentChecks.isNotEmpty(id);
		this.clientId = id;
	}

	@Override
	public List<PaymentMeanDto> execute() throws BusinessException {
		return DtoAssembler.toPaymentMeanDtoList(
				paymentMeanRepository.findByClientId(clientId));
	}

}
