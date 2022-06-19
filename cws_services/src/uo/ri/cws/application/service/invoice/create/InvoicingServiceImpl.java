package uo.ri.cws.application.service.invoice.create;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.command.CreateInvoiceFor;
import uo.ri.cws.application.service.invoice.create.command.FindNotInvoicedWorkOrdersByClientDni;
import uo.ri.cws.application.service.invoice.create.command.FindPaymentMeansByClientDni;
import uo.ri.cws.application.service.invoice.create.command.FindWorkOrdersByClientDni;
import uo.ri.cws.application.service.invoice.create.command.SettleInvoice;
import uo.ri.cws.application.service.paymentmean.crud.command.FindInvoiceByNumber;
import uo.ri.cws.application.service.paymentmean.crud.command.FindWorkOrdersByPlateNumber;
import uo.ri.cws.application.util.command.CommandExecutor;

public class InvoicingServiceImpl implements InvoicingService {

    private CommandExecutor executor = Factory.executor.forExecutor();

    @Override
    public InvoiceDto createInvoiceFor(List<String> woIds)
	    throws BusinessException {

	return executor.execute(new CreateInvoiceFor(woIds));
    }

    @Override
    public List<InvoicingWorkOrderDto> findWorkOrdersByClientDni(String dni)
	    throws BusinessException {
	return executor.execute(new FindWorkOrdersByClientDni(dni));
    }

    @Override
    public List<InvoicingPaymentMeanDto> findPayMeansByClientDni(String dni)
	    throws BusinessException {
	return executor.execute(new FindPaymentMeansByClientDni(dni));
    }

    @Override
    public List<InvoicingWorkOrderDto> findNotInvoicedWorkOrdersByClientDni(
	    String dni) throws BusinessException {
	return executor.execute(new FindNotInvoicedWorkOrdersByClientDni(dni));
    }

    @Override
    public List<InvoicingWorkOrderDto> findWorkOrdersByPlateNumber(String plate)
	    throws BusinessException {
	return executor.execute(new FindWorkOrdersByPlateNumber(plate));
    }

    @Override
    public Optional<InvoiceDto> findInvoiceByNumber(Long number)
	    throws BusinessException {
	return executor.execute(new FindInvoiceByNumber(number));
    }

    @Override
    public void settleInvoice(String invoiceId, Map<String, Double> charges)
	    throws BusinessException {
	executor.execute(new SettleInvoice(invoiceId, charges));
    }

    public static class InvoicingVoucherDto extends InvoicingPaymentMeanDto {
    }

    public static class InvoicingCashDto extends InvoicingPaymentMeanDto {
    }

    public static class InvoicingCardDto extends InvoicingPaymentMeanDto {
    }

}
