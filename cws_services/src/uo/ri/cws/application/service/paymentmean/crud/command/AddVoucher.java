package uo.ri.cws.application.service.paymentmean.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Voucher;

public class AddVoucher implements Command<VoucherDto>{

	private VoucherDto voucher;
	PaymentMeanRepository paymentMeanRepository = Factory.repository.forPaymentMean();
	ClientRepository clientRepository = Factory.repository.forClient();
	
	public AddVoucher(VoucherDto voucher) {
		ArgumentChecks.isNotNull(voucher);
		ArgumentChecks.isNotEmpty(voucher.code);
		ArgumentChecks.isNotEmpty(voucher.description);
		ArgumentChecks.isNotEmpty(voucher.clientId);
		ArgumentChecks.isTrue(voucher.balance>=0);
		this.voucher = voucher;
	}

	@Override
	public VoucherDto execute() throws BusinessException {
		Optional<Client> oClient = clientRepository.findById(voucher.clientId);
		BusinessChecks.exists(oClient);
		BusinessChecks.isTrue(paymentMeanRepository.findVoucherByCode(voucher.code).isEmpty(), 
				"There's already a voucher with this code.");
		
		Voucher newVoucher = new Voucher(voucher.code, voucher.description, voucher.balance, oClient.get());
		paymentMeanRepository.add(newVoucher);
		
		voucher.id = newVoucher.getId();
		return voucher;
	}

}
