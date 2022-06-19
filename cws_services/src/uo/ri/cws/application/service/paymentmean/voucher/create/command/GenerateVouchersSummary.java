package uo.ri.cws.application.service.paymentmean.voucher.create.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.VoucherService.VoucherSummaryDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;

public class GenerateVouchersSummary
	implements Command<List<VoucherSummaryDto>> {

    private PaymentMeanRepository paymentMeanRepository = Factory.repository
	    .forPaymentMean();
    private ClientRepository clientRepository = Factory.repository.forClient();

    @Override
    public List<VoucherSummaryDto> execute() throws BusinessException {
	List<VoucherSummaryDto> result = new ArrayList<>();

	List<Client> allClients = clientRepository.findAll();
	Object[] voucherSummary;
	for (Client client : allClients) {
	    voucherSummary = paymentMeanRepository
		    .findAggregateVoucherDataByClientId(client.getId());
	    // Si no hay vouchers, pasamos al siguiente
	    if (!voucherSummary[0].equals(0L))
		result.add(toVoucherSummaryDto(client, voucherSummary));
	}
	return result;
    }

    private VoucherSummaryDto toVoucherSummaryDto(Client client,
	    Object[] voucherSummary) {
	VoucherSummaryDto dto = new VoucherSummaryDto();
	dto.dni = client.getDni();
	dto.name = client.getName();
	dto.surname = client.getSurname();
	dto.issued = (long) voucherSummary[0];
	dto.availableBalance = (double) voucherSummary[1];
	dto.consumed = (double) voucherSummary[2];
	dto.totalAmount = dto.availableBalance + dto.consumed;
	return dto;
    }

}
