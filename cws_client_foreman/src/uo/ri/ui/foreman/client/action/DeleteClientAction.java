package uo.ri.ui.foreman.client.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.client.ClientCrudService;

public class DeleteClientAction implements Action {

	@Override
	public void execute() throws Exception {
		String id = Console.readString("Client id");
		
		ClientCrudService s = Factory.service.forClientCrudService();
		s.deleteClient( id );
		
		Console.println("The client has been removed");
	}

}
