package uo.ri.cws.application.service.client.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Recommendation;

public class FindClientsRecommendedBy implements Command<List<ClientDto>> {

	private String sponsorID;
	private ClientRepository repo = Factory.repository.forClient();

	public FindClientsRecommendedBy(String sponsorID) {
		ArgumentChecks.isNotEmpty(sponsorID);
		this.sponsorID = sponsorID;
	}

	@Override
	public List<ClientDto> execute() throws BusinessException {
		ArrayList<ClientDto> res = new ArrayList<>();

		Optional<Client> co = repo.findById(sponsorID);
		if (co.isPresent()) {
			Client client = co.get();
			Set<Recommendation> recommendations = client.getSponsored();
			for (Recommendation recommendation : recommendations) {
				res.add(DtoAssembler.toDto(recommendation.getRecommended()));
			}
		}
		return res;
	}

}
