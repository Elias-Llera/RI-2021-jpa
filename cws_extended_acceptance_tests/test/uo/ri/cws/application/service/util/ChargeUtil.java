package uo.ri.cws.application.service.util;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import uo.ri.cws.application.service.invoice.InvoicingService.ChargeDto;
import uo.ri.cws.application.service.util.sql.AddChargeSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindAllChargesSqlUnitOfWork;

public class ChargeUtil {

	private ChargeDto dto = createDefaultCharge();

	public ChargeDto get() {
		return dto;
	}

	public ChargeUtil forPaymentMean(String pay) {
		dto.paymentMean_id = pay;
		return this;
	}

	public ChargeUtil forInvoice(String invoice) {
		dto.invoice_id = invoice;
		return this;
	}

	private ChargeDto createDefaultCharge() {

		Random r = new Random();
		int rangeMin = 10;
		int rangeMax = 100;
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();

		ChargeDto res = new ChargeDto();
		res.id = UUID.randomUUID().toString();
		res.version = 1L;
		res.amount = randomValue;
		return res;
	}

	public ChargeUtil register() {
		new AddChargeSqlUnitOfWork(dto).execute();
		return this;
	}

	public List<ChargeDto> findAll() {
		FindAllChargesSqlUnitOfWork finder = new FindAllChargesSqlUnitOfWork();
		finder.execute();
		return finder.get();
	}
}
