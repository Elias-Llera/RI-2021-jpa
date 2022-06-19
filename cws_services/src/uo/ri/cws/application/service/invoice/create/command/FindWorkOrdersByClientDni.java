package uo.ri.cws.application.service.invoice.create.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.DtoAssembler;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.WorkOrder;

public class FindWorkOrdersByClientDni
	implements Command<List<InvoicingWorkOrderDto>> {

    private String clientDni;
    private ClientRepository clientRepo = Factory.repository.forClient();

    public FindWorkOrdersByClientDni(String dni) {
	ArgumentChecks.isNotEmpty(dni);
	this.clientDni = dni;
    }

    @Override
    public List<InvoicingWorkOrderDto> execute() throws BusinessException {
	Optional<Client> client = clientRepo.findByDni(clientDni);
	if (client.isEmpty()) {
	    throw new BusinessException("The client does not exist.");
	}

	Set<Vehicle> clientVehicles = client.get().getVehicles();

	List<WorkOrder> workOrders = new ArrayList<>();

	for (Vehicle vehicle : clientVehicles) {
	    workOrders.addAll(vehicle.getWorkOrders());
	}

	return DtoAssembler.toWorkOrderDtoList(workOrders);
    }

}
