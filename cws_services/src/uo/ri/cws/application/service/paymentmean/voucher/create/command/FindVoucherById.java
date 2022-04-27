package uo.ri.cws.application.service.paymentmean.voucher.create.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Voucher;

public class FindVoucherById implements Command<Optional<VoucherDto>>{

	private String id;
	private PaymentMeanRepository paymentMeanRepository = Factory.repository.forPaymentMean();
	
	public FindVoucherById(String id) {
		ArgumentChecks.isNotEmpty(id);
		this.id = id;
	}

	@Override
	public Optional<VoucherDto> execute() throws BusinessException {
		return toDto(paymentMeanRepository.findVoucherByCode(id));
	}
	
	public Optional<VoucherDto> toDto(Optional<Voucher> voucher) {
		if(voucher.isEmpty()) {
			return Optional.empty();
		}
		
		VoucherDto dto = new VoucherDto();
		dto.id = voucher.get().getId();
        dto.version = voucher.get().getVersion();
        dto.clientId = voucher.get().getClient().getId();
        dto.accumulated = voucher.get().getAccumulated();
        dto.code = voucher.get().getCode();
        dto.description = voucher.get().getDescription();
        dto.balance = voucher.get().getAvailable();
        
		return Optional.of(dto);
		
	}

}
