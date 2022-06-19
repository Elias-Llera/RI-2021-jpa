package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;
import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.DtoAssembler;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingPaymentMeanDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.PaymentMean;

public class FindPaymentMeansByClientDni
	implements Command<List<InvoicingPaymentMeanDto>> {

    private String clientDni;
    private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();
    private ClientRepository clientRepo = Factory.repository.forClient();

    public FindPaymentMeansByClientDni(String dni) {
	ArgumentChecks.isNotEmpty(dni);
	this.clientDni = dni;
    }

    @Override
    public List<InvoicingPaymentMeanDto> execute() throws BusinessException {
	Optional<Client> client = clientRepo.findByDni(clientDni);
	if (client.isEmpty()) {
	    throw new BusinessException("The dni does not exist");
	}
	List<PaymentMean> pms = pmRepo.findByClientId(client.get().getId());
	return DtoAssembler.toPaymentMeanDtoList(pms);
    }

}
