package uo.ri.cws.application.service.client.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.repository.RecommendationRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Address;
import uo.ri.cws.domain.Cash;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Recommendation;

public class AddClient implements Command<ClientDto> {

	private ClientDto dto;
	private String recommenderId;
	private ClientRepository clientRepo = Factory.repository.forClient();
	private PaymentMeanRepository pmRepo = Factory.repository.forPaymentMean();
	private RecommendationRepository recommendRepo = Factory.repository
			.forRecomendacion();

	public AddClient(ClientDto dto, String recommenderId) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotEmpty(dto.dni);
		this.dto = dto;
		this.recommenderId = recommenderId;
	}

	@Override
	public ClientDto execute() throws BusinessException {
		checkDoesNotExist();

		Client client = new Client(dto.dni, dto.name, dto.surname);
		client.setAddress(new Address(dto.addressStreet, dto.addressCity,
				dto.addressZipcode));
		client.setEmail(dto.email);
		client.setPhone(dto.phone);

		clientRepo.add(client);

		Cash cash = new Cash(client);
		pmRepo.add(cash);

		if (recommenderId != null && !recommenderId.isBlank()) {
			createRecommendation(client);
		}

		dto.id = client.getId();

		return dto;
	}

	private void createRecommendation(Client client) throws BusinessException {
		Optional<Client> sponsor = clientRepo.findById(recommenderId);
		if (sponsor.isPresent()) {
			Recommendation recommendation = new Recommendation(sponsor.get(),
					client);
			recommendRepo.add(recommendation);
		} else {
			throw new BusinessException("The recommender does not exist.");
		}
	}

	private void checkDoesNotExist() throws BusinessException {
		Optional<Client> cm = clientRepo.findByDni(dto.dni);
		BusinessChecks.isTrue(cm.isEmpty());
	}

}
