package uo.ri.cws.application.service.paymentmean.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.VehicleRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.DtoAssembler;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.WorkOrder;

public class FindWorkOrdersByPlateNumber
	implements Command<List<InvoicingWorkOrderDto>> {

    private String plate;
    private VehicleRepository vehicleRepo = Factory.repository.forVehicle();

    public FindWorkOrdersByPlateNumber(String plate) {
	ArgumentChecks.isNotEmpty(plate);
	this.plate = plate;
    }

    @Override
    public List<InvoicingWorkOrderDto> execute() throws BusinessException {
	Optional<Vehicle> vehicle = vehicleRepo.findByPlate(plate);
	if (vehicle.isEmpty()) {
	    throw new BusinessException("The client does not exist.");
	}

	List<WorkOrder> workOrders = new ArrayList<>(
		vehicle.get().getWorkOrders());

	return DtoAssembler.toWorkOrderDtoList(workOrders);
    }

}
