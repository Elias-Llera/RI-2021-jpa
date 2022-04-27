package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;

public class CreateInvoiceFor implements Command<InvoiceDto> {

	private List<String> workOrderIds;
	private WorkOrderRepository wrkrsRepo = Factory.repository.forWorkOrder();
	private InvoiceRepository invsRepo = Factory.repository.forInvoice();

	public CreateInvoiceFor(List<String> workOrderIds) {
		validate(workOrderIds);
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		Long number = invsRepo.getNextInvoiceNumber();
		List<WorkOrder> workOrders = wrkrsRepo.findByIds(workOrderIds);

		BusinessChecks.isTrue(workOrders.size() == workOrderIds.size(),
				"Some work orders do not exist");

		checkAllFinished(workOrders);

		Invoice i = new Invoice(number, workOrders);
		invsRepo.add(i);

		return DtoAssembler.toDto(i);
	}

	private void validate(List<String> workOrderIds) {
		ArgumentChecks.isNotNull(workOrderIds);
		ArgumentChecks.isTrue(!workOrderIds.isEmpty());
		for (String id : workOrderIds) {
			ArgumentChecks.isNotEmpty(id);
		}
	}

	private void checkAllFinished(List<WorkOrder> workOrders)
			throws BusinessException {
		for (WorkOrder workOrder : workOrders) {
			BusinessChecks.isTrue(workOrder.isFinished());
		}
	}

}