package uo.ri.cws.application.service.client.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Address;
import uo.ri.cws.domain.Client;

public class UpdateClient implements Command<Void> {

	private ClientDto dto;
	private ClientRepository repo = Factory.repository.forClient();

	public UpdateClient(ClientDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotEmpty(dto.id);
		ArgumentChecks.isNotEmpty(dto.name);
		ArgumentChecks.isNotEmpty(dto.surname);
		ArgumentChecks.isNotEmpty(dto.email);
		ArgumentChecks.isNotEmpty(dto.phone);
		ArgumentChecks.isNotEmpty(dto.addressCity);
		ArgumentChecks.isNotEmpty(dto.addressStreet);
		ArgumentChecks.isNotEmpty(dto.addressZipcode);
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<Client> om = repo.findById(dto.id);

		BusinessChecks.exists(om, "The mechanic does not exist.");

		Client c = om.get();

		BusinessChecks.hasVersion(c, dto.version);

		c.setName(dto.name);
		c.setSurname(dto.surname);
		c.setEmail(dto.email);
		c.setPhone(dto.phone);
		c.setAddress(new Address(dto.addressStreet, dto.addressCity,
				dto.addressZipcode));

		return null;
	}

}
