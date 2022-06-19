package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.DtoAssembler;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class FindNotInvoicedWorkOrdersByClientDni
	implements Command<List<InvoicingWorkOrderDto>> {

    private String clientDni;
    private WorkOrderRepository woRepo = Factory.repository.forWorkOrder();
    private ClientRepository clientRepo = Factory.repository.forClient();

    public FindNotInvoicedWorkOrdersByClientDni(String dni) {
	ArgumentChecks.isNotEmpty(dni);
	this.clientDni = dni;
    }

    @Override
    public List<InvoicingWorkOrderDto> execute() throws BusinessException {
	BusinessChecks.exists(clientRepo.findByDni(clientDni));
	List<WorkOrder> workOrders = woRepo
		.findNotInvoicedWorkOrdersByClientDni(clientDni);
	return DtoAssembler.toWorkOrderDtoList(workOrders);
    }

}
