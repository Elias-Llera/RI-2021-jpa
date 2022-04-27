package uo.ri.ui.foreman.client.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.ui.util.Printer;

public class ListRecomendedClientsAction implements Action {

	@Override
	public void execute() throws Exception {
		String id = Console.readString("Client id");
		
		ClientCrudService s = Factory.service.forClientCrudService();
		List<ClientDto> clients = s.findClientsRecommendedBy( id );
		
		for(ClientDto dto: clients) {
			Printer.printClientDetail( dto );
		}
	}

}
