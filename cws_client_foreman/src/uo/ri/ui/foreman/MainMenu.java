package uo.ri.ui.foreman;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.ui.foreman.client.ClientsMenu;
import uo.ri.ui.foreman.reception.ReceptionMenu;
import uo.ri.ui.foreman.vehicle.VehiclesMenu;

public class MainMenu extends BaseMenu {{
		menuOptions = new Object[][] { 
			{ "Foreman", null },
			{ "Vehicle reception", 		ReceptionMenu.class }, 
			{ "Client management", 		ClientsMenu.class },
			{ "Vehicle management", 	VehiclesMenu.class },
			{ "Review client history", 	NotYetImplementedAction.class }, 
		};
}}