package uo.ri.ui.foreman.reception;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;

public class ReceptionMenu extends BaseMenu {

	public ReceptionMenu() {
		menuOptions = new Object[][] { 
			{"Foreman > Vehicle reception", null},
			
			{"Register work order", 	NotYetImplementedAction.class }, 
			{"Update workorder", 		NotYetImplementedAction.class },
			{"Remove workorder", 		NotYetImplementedAction.class },
			{"", null},
			{"List work orders", 		NotYetImplementedAction.class }, 
			{"View work order detail", 	NotYetImplementedAction.class },
			{"", null},
			{"List certified mechanics",NotYetImplementedAction.class }, 
			{"Assign a work order",  	NotYetImplementedAction.class },
		};
	}

}
