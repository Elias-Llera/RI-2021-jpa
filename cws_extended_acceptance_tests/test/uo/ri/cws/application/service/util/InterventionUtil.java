package uo.ri.cws.application.service.util;

import java.time.LocalDateTime;
import java.util.UUID;

import uo.ri.cws.application.service.util.sql.AddInterventionSqlUnitOfWork;

public class InterventionUtil {

	private InterventionDto dto = createDefaultDto();

	public InterventionUtil forWorkOrder(String wId) {
		dto.workOrderId = wId;
		return this;
	}

	public InterventionUtil forMechanic(String mId) {
		dto.mechanicId = mId;
		return this;
	}

	public InterventionUtil register() {
		new AddInterventionSqlUnitOfWork(dto).execute();
		return this;
	}

	public InterventionDto get() {
		return dto;
	}

	public InterventionDto createDefaultDto() {
		InterventionDto res = new InterventionDto();
		res.id = UUID.randomUUID().toString();
		res.version = 1;
		res.minutes = 0;
		res.date = LocalDateTime.now();
		return res;
	}

	public class InterventionDto {

		public String id;
		public int minutes;
		public long version;
		public LocalDateTime date;
		public String mechanicId;
		public String workOrderId;

	}

}
