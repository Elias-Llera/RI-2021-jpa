package uo.ri.ui.foreman.client.action;

import java.util.List;

import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.ui.util.Printer;

public class ListClientsAction implements Action {

	@Override
	public void execute() throws Exception {
		ClientCrudService s = Factory.service.forClientCrudService();
		List<ClientDto> clients = s.findAllClients();
		
		for(ClientDto dto: clients) {
			Printer.printClientDetail( dto );
		}
	}

}
