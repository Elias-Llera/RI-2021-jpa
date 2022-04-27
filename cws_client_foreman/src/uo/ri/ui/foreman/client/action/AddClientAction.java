package uo.ri.ui.foreman.client.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;

public class AddClientAction implements Action {

	@Override
	public void execute() throws Exception {
		ClientDto c = new ClientDto();
		c.dni = Console.readString("DNI");
		c.name = Console.readString("Name");
		c.surname = Console.readString("Surname");
		c.phone = Console.readString("Phone");
		c.email = Console.readString("Email");
		c.addressStreet = Console.readString("Street");
		c.addressCity = Console.readString("City");
		c.addressZipcode = Console.readString("Zipcode");

		String recommenderId = Console.readString("Sponsor client id (space if not)");
		recommenderId = recommenderId.isBlank() ? null : recommenderId;
		
		ClientCrudService s = Factory.service.forClientCrudService();
		c = s.addClient( c, recommenderId );
		
		Console.println("Client registered with id: " + c.id);
		
	}

}
