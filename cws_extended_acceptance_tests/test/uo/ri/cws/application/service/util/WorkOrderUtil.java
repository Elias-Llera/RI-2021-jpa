package uo.ri.cws.application.service.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;

import uo.ri.cws.application.service.util.sql.AddWorkOrderSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindWorkOrderSqlUnitOfWork;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;

public class WorkOrderUtil {

	private WorkOrderDto dto = createDefaultWorkOrderDto();

	public WorkOrderDto get() {
		return dto;
	}

	private LocalDateTime randomDate() {
//		It is as easy as, assume d1 and d2 being LocalDate, with d1 < d2 (pseudo-code):

		String str = "2020-04-08 12:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		LocalDateTime dateBefore = LocalDateTime.parse(str, formatter);
		LocalDateTime dateAfter = LocalDateTime.now();
		long noOfDaysBetween = ChronoUnit.MILLIS.between(dateBefore, dateAfter);
		LocalDateTime randomDate = dateBefore.plusDays(
				ThreadLocalRandom.current().nextLong(noOfDaysBetween + 1)
		);
		return randomDate;
	}

	private WorkOrderDto createDefaultWorkOrderDto() {
		WorkOrderDto res = new WorkOrderDto();

		res.id = UUID.randomUUID().toString();
		res.version = 1L;
		res.date = randomDate();
		res.description = RandomStringUtils.randomAlphabetic(10);
		res.status = "OPEN";
		res.total = 0.0;

		return res;
	}

	public WorkOrderUtil forMechanic(String mId) {
		dto.mechanicId = mId;
		return this;
	}

	public WorkOrderUtil withStatus(String status) {
		dto.status = status;
		return this;
	}

	public WorkOrderUtil withAmount(double amount) {
		dto.total = amount;
		return this;
	}

	public WorkOrderUtil withDate(String date) {
		dto.date = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return this;
	}

	public WorkOrderUtil withUsedForVoucher(String arg) {
		dto.usedForVoucher = Boolean.valueOf(arg);
		return this;
	}

	public WorkOrderUtil notUserForVoucher() {
		dto.usedForVoucher = false;
		return this;
	}

	public WorkOrderUtil withInvoice(String invoiceid) {
		dto.invoiceId = invoiceid;
		return this;
	}

	public WorkOrderUtil forVehicle(String vId) {
		dto.vehicleId = vId;
		return this;
	}

	public WorkOrderUtil findById(String id) {
		dto = new FindWorkOrderSqlUnitOfWork(id).execute().get().get();
		return this;
	}

	public WorkOrderUtil register() {
		new AddWorkOrderSqlUnitOfWork(dto).execute();
		return this;
	}

}
