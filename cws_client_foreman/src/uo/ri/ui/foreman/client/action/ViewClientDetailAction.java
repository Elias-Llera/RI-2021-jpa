package uo.ri.ui.foreman.client.action;

import java.util.Optional;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.ui.util.Printer;

public class ViewClientDetailAction implements Action {

	@Override
	public void execute() throws Exception {
		String id = Console.readString("Client id");
		
		ClientCrudService s = Factory.service.forClientCrudService();
		Optional<ClientDto> oc = s.findClientById(id);
		
		if ( oc.isEmpty()) {
			throw new BusinessException("The client does not exist");
		}
		
		ClientDto c = oc.get();
		Printer.printClientDetail(c);
	}

}
