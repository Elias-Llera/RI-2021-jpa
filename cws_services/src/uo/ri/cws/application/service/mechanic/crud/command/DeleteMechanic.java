package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class DeleteMechanic implements Command<Void> {

	private String mechanicId;
	private MechanicRepository repo = Factory.repository.forMechanic();

	public DeleteMechanic(String mechanicId) {
		ArgumentChecks.isNotEmpty(mechanicId);
		this.mechanicId = mechanicId;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<Mechanic> m = repo.findById(mechanicId);

		checkCanBeDeleted(m);

		repo.remove(m.get());

		return null;
	}

	private void checkCanBeDeleted(Optional<Mechanic> m)
			throws BusinessException {
		BusinessChecks.exists(m, "Mechanic does not exist");
		if (!m.get().getAssigned().isEmpty()
				|| !m.get().getInterventions().isEmpty()) {
			throw new BusinessException("The mechanic has dependencies");
		}
	}

}
