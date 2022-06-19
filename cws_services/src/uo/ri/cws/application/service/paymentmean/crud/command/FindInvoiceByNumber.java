package uo.ri.cws.application.service.paymentmean.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;

public class FindInvoiceByNumber implements Command<Optional<InvoiceDto>> {

    private Long number;
    private InvoiceRepository invoiceRepo = Factory.repository.forInvoice();

    public FindInvoiceByNumber(Long number) {
	ArgumentChecks.isNotNull(number);
	this.number = number;
    }

    @Override
    public Optional<InvoiceDto> execute() throws BusinessException {
	Optional<Invoice> invoice = invoiceRepo.findByNumber(number);

	if (invoice.isEmpty()) {
	    return Optional.empty();
	}

	return Optional.of(DtoAssembler.toDto(invoice.get()));
    }

}
