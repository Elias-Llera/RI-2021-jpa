package uo.ri.ui.foreman.client;

import alb.util.menu.BaseMenu;
import uo.ri.ui.foreman.client.action.AddClientAction;
import uo.ri.ui.foreman.client.action.DeleteClientAction;
import uo.ri.ui.foreman.client.action.ListClientsAction;
import uo.ri.ui.foreman.client.action.ListRecomendedClientsAction;
import uo.ri.ui.foreman.client.action.UpdateClientAction;
import uo.ri.ui.foreman.client.action.ViewClientDetailAction;

public class ClientsMenu extends BaseMenu {

	public ClientsMenu() {
		menuOptions = new Object[][] { 
			{ "Foreman > Client management", null },

			{ "Register a client", 	AddClientAction.class }, 
			{ "Update a client", 	UpdateClientAction.class }, 
			{ "View client details",	ViewClientDetailAction.class }, 
			{ "Disable a client", 	DeleteClientAction.class }, 
			{ "List all clients", 	ListClientsAction.class }, 
			{ "List clients recommended by",	ListRecomendedClientsAction.class }, 
		};
	}

}
