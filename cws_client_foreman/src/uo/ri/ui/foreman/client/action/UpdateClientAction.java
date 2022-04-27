package uo.ri.ui.foreman.client.action;

import java.util.Optional;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.ui.util.Printer;

public class UpdateClientAction implements Action {

	@Override
	public void execute() throws Exception {
		ClientCrudService s = Factory.service.forClientCrudService();
		
		String id = Console.readString("Client id");
		Optional<ClientDto> oc = s.findClientById( id );
		if ( oc.isEmpty()) {
			throw new BusinessException("The client does not exist");
		}
		ClientDto c = oc.get();
		Printer.printClientDetail( c );

		c.name = Console.readString("Name");
		c.surname = Console.readString("Surname");
		c.phone = Console.readString("Phone");
		c.email = Console.readString("Email");
		c.addressStreet = Console.readString("Street");
		c.addressCity = Console.readString("City");
		c.addressZipcode = Console.readString("Zipcode");

		s.updateClient( c );
		
		Console.println("Client updated");		
	}
	
}
