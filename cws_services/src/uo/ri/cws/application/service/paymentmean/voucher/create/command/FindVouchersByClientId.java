package uo.ri.cws.application.service.paymentmean.voucher.create.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindVouchersByClientId implements Command<List<VoucherDto>>{

	
	private String clientId;
	private PaymentMeanRepository paymentMeanRepository = Factory.repository.forPaymentMean();
	private ClientRepository clientRepository = Factory.repository.forClient();
	
	public FindVouchersByClientId(String clientId) {
		this.clientId = clientId;
	}
	
	@Override
	public List<VoucherDto> execute() throws BusinessException {
		BusinessChecks.isNotEmpty(clientId, "The id is not valid.");
		BusinessChecks.exists(clientRepository.findById(clientId), "The client does not exist.");
		
		return DtoAssembler.toVoucherDtoList(paymentMeanRepository.findVouchersByClientId(clientId));
	}

}
