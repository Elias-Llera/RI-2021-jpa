package uo.ri.cws.application.service.invoice;

import java.util.List;
import java.util.stream.Collectors;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
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
}
