package uo.ri.cws.application.service.client.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;

public class FindClientById implements Command<Optional<ClientDto>> {

    private String idClient;
    private ClientRepository repo = Factory.repository.forClient();

    public FindClientById(String idClient) {
	ArgumentChecks.isNotEmpty(idClient);
	this.idClient = idClient;
    }

    @Override
    public Optional<ClientDto> execute() throws BusinessException {
	Optional<Client> c = repo.findById(idClient);

	return c.isEmpty() ? Optional.empty()
		: Optional.of(DtoAssembler.toDto(c.get()));
    }

}
