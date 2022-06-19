package uo.ri.cws.application.service.invoice;

import java.util.List;
import java.util.stream.Collectors;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingPaymentMeanDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.InvoicingServiceImpl.InvoicingCardDto;
import uo.ri.cws.application.service.invoice.create.InvoicingServiceImpl.InvoicingCashDto;
import uo.ri.cws.application.service.invoice.create.InvoicingServiceImpl.InvoicingVoucherDto;
import uo.ri.cws.domain.Cash;
import uo.ri.cws.domain.CreditCard;
import uo.ri.cws.domain.PaymentMean;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.domain.WorkOrder;

public class DtoAssembler {

    public static InvoicingWorkOrderDto toDto(WorkOrder a) {
	InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
	dto.id = a.getId();
	dto.version = a.getVersion();

	dto.description = a.getDescription();
	dto.date = a.getDate();
	dto.total = a.getAmount();
	dto.status = a.getStatus().toString();

	return dto;
    }

    public static List<InvoicingWorkOrderDto> toWorkOrderDtoList(
	    List<WorkOrder> list) {
	return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
    }

    public static InvoicingPaymentMeanDto toDto(PaymentMean pm) {
	if (pm instanceof Voucher) {
	    return toDto((Voucher) pm);
	} else if (pm instanceof CreditCard) {
	    return toDto((CreditCard) pm);
	} else if (pm instanceof Cash) {
	    return toDto((Cash) pm);
	} else {
	    throw new RuntimeException("Unexpected type of payment mean");
	}
    }

    public static InvoicingVoucherDto toDto(Voucher v) {
	InvoicingVoucherDto dto = new InvoicingVoucherDto();
	dto.id = v.getId();
	dto.version = v.getVersion();

	dto.clientId = v.getClient().getId();
	dto.accumulated = v.getAccumulated();
	return dto;
    }

    public static InvoicingCardDto toDto(CreditCard cc) {
	InvoicingCardDto dto = new InvoicingCardDto();
	dto.id = cc.getId();
	dto.version = cc.getVersion();

	dto.clientId = cc.getClient().getId();
	dto.accumulated = cc.getAccumulated();
	return dto;
    }

    public static InvoicingCashDto toDto(Cash m) {
	InvoicingCashDto dto = new InvoicingCashDto();
	dto.id = m.getId();
	dto.version = m.getVersion();

	dto.clientId = m.getClient().getId();
	dto.accumulated = m.getAccumulated();
	return dto;
    }

    public static List<InvoicingPaymentMeanDto> toPaymentMeanDtoList(
	    List<PaymentMean> pms) {
	return pms.stream().map(a -> toDto(a)).collect(Collectors.toList());
    }
}
