package uo.ri.cws.application.service.util;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.service.util.sql.AddVoucherSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindVoucherByCodeSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindVoucherByIdSqlUnitOfWork;

public class VoucherUtil {

	private VoucherDto dto = createDefaultVoucher();

	public VoucherDto get() {
		return dto;
	}

	private VoucherDto createDefaultVoucher() {

		VoucherDto res = new VoucherDto();

		Random r = new Random();
		int rangeMin = 10;
		int rangeMax = 100;
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();

		res.id = UUID.randomUUID().toString();
		res.version = 1L;
		res.accumulated = 0.0;
		res.balance = randomValue;
		res.description = UUID.randomUUID().toString();
		res.code = UUID.randomUUID().toString();
		return res;
	}

	public VoucherUtil withId(String id) {
		dto.id = id;
		return this;
	}

	public VoucherUtil forClient(String id) {
		dto.clientId = id;
		return this;
	}

	public VoucherUtil withCode(String arg) {
		dto.code = arg;
		return this;
	}

	public VoucherUtil withAccumulated(double accumulated) {
		dto.accumulated = accumulated;
		return this;
	}

	public VoucherUtil withBalance(double balance) {
		dto.balance = balance;
		return this;
	}

	public VoucherUtil withDescription(String description) {
		dto.description = description;
		return this;
	}

	public Optional<VoucherDto> findById(String id) {
		return new FindVoucherByIdSqlUnitOfWork(id).execute().get();
	}

	public Optional<VoucherDto> findByCode(String code) {
		return new FindVoucherByCodeSqlUnitOfWork(code).execute().get();
	}

	public VoucherUtil register() {
		new AddVoucherSqlUnitOfWork( dto ).execute();
		return this;
	}

}
