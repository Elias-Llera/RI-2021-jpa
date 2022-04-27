package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class FindMechanicByDni implements Command<Optional<MechanicDto>> {

	private String dniMechanic;
	private MechanicRepository repo = Factory.repository.forMechanic();

	public FindMechanicByDni(String dniMechanic) {
		ArgumentChecks.isNotEmpty(dniMechanic);
		this.dniMechanic = dniMechanic;
	}

	@Override
	public Optional<MechanicDto> execute() throws BusinessException {
		Optional<Mechanic> m = repo.findByDni(dniMechanic);

		return m.isEmpty() ? Optional.empty()
				: Optional.of(DtoAssembler.toDto(m.get()));
	}

}
