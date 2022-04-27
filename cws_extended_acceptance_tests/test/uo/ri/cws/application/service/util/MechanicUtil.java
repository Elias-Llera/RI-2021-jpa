package uo.ri.cws.application.service.util;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.util.sql.AddMechanicSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindMechanicByIdSqlUnitOfWork;

public class MechanicUtil {

	private MechanicDto dto = createDefaultDto();

	public MechanicUtil unique() {
		this.dto.dni = RandomStringUtils.randomAlphanumeric(9);
		this.dto.name = RandomStringUtils.randomAlphabetic(4) + "-name";
		this.dto.surname = RandomStringUtils.randomAlphabetic(4) + "-surname";
		return this;
	}

	public MechanicUtil withId(String arg) {
		this.dto.id = arg;
		return this;
	}

	public MechanicUtil withDni(String arg) {
		this.dto.dni = arg;
		return this;
	}

	public MechanicUtil withName(String name) {
		this.dto.name = name;
		return this;
	}

	public MechanicUtil withSurname(String arg) {
		this.dto.surname = arg;
		return this;
	}

	public MechanicUtil findById(String id) throws BusinessException {
		this.dto = new FindMechanicByIdSqlUnitOfWork(id).execute().get().orElse(null);
		return this;
	}

	public MechanicUtil register() throws BusinessException {
		new AddMechanicSqlUnitOfWork(dto).execute();
		return this;
	}

	public MechanicUtil registerIfNew() throws BusinessException {
		Optional<MechanicDto> op = new FindMechanicByIdSqlUnitOfWork(dto.id).execute().get();
		if (op.isEmpty()) {
			register();
		} else {
			dto.id = op.get().id;
		}
		return this;
	}

	public MechanicDto get() {
		return dto;
	}

	private MechanicDto createDefaultDto() {
		MechanicDto dto = new MechanicDto();
		dto.id = UUID.randomUUID().toString();
		dto.version = 1L;
		dto.name = "dummy-mechanic-name";
		dto.dni = "dummy-mechanic-dni";
		dto.surname = "dummy-mechanic-surname";
		return dto;
	}

}
