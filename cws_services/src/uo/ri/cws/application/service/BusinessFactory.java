package uo.ri.cws.application.service;

import uo.ri.cws.application.ServiceFactory;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientHistoryService;
import uo.ri.cws.application.service.client.crud.ClientCrudServiceImpl;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.InvoicingServiceImpl;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService;
import uo.ri.cws.application.service.paymentmean.VoucherService;
import uo.ri.cws.application.service.paymentmean.crud.PaymentMeanCrudServiceImpl;
import uo.ri.cws.application.service.paymentmean.voucher.create.VoucherServiceImpl;
import uo.ri.cws.application.service.sparepart.SparePartCrudService;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.crud.VehicleCrudServiceImpl;
import uo.ri.cws.application.service.vehicletype.VehicleTypeCrudService;
import uo.ri.cws.application.service.workorder.CloseWorkOrderService;
import uo.ri.cws.application.service.workorder.ViewAssignedWorkOrdersService;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;

public class BusinessFactory implements ServiceFactory {

    @Override
    public MechanicCrudService forMechanicCrudService() {
	return new MechanicCrudServiceImpl();
    }

    @Override
    public InvoicingService forCreateInvoiceService() {
	return new InvoicingServiceImpl();
    }

    @Override
    public VehicleCrudService forVehicleCrudService() {
	return new VehicleCrudServiceImpl();
    }

    @Override
    public CloseWorkOrderService forClosingBreakdown() {
	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public VehicleTypeCrudService forVehicleTypeCrudService() {
	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public SparePartCrudService forSparePartCrudService() {
	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public ClientHistoryService forClientHistoryService() {
	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public WorkOrderCrudService forWorkOrderCrudService() {
	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public ViewAssignedWorkOrdersService forViewAssignedWorkOrdersService() {
	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public VoucherService forVoucherService() {
	return new VoucherServiceImpl();
    }

    @Override
    public PaymentMeanCrudService forPaymentMeanService() {
	return new PaymentMeanCrudServiceImpl();
    }

    @Override
    public ClientCrudService forClientCrudService() {
	return new ClientCrudServiceImpl();
    }

}
