package uo.ri.cws.application.service.client.crud.command;

import java.util.List;
import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.PaymentMean;

public class DeleteClient implements Command<Void> {

	private String idClient;
	private ClientRepository repo = Factory.repository.forClient();
	private PaymentMeanRepository repoCash = Factory.repository
			.forPaymentMean();

	public DeleteClient(String idClient) {
		ArgumentChecks.isNotEmpty(idClient);
		this.idClient = idClient;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<Client> m = repo.findById(idClient);

		checkCanBeDeleted(m);

		List<PaymentMean> paymentMeans = repoCash
				.findByClientId(m.get().getId());

		for (PaymentMean paymentMean : paymentMeans) {
			repoCash.remove(paymentMean);
		}

		repo.remove(m.get());

		return null;
	}

	private void checkCanBeDeleted(Optional<Client> c)
			throws BusinessException {
		BusinessChecks.exists(c, "Client does not exist");
		if (!c.get().getVehicles().isEmpty()
				|| !c.get().getSponsored().isEmpty()) {
			throw new BusinessException("The client has dependencies");
		}
	}

}
