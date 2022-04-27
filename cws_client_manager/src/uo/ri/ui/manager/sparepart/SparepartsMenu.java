package uo.ri.ui.manager.sparepart;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;

public class SparepartsMenu extends BaseMenu {

	public SparepartsMenu() {
		menuOptions = new Object[][] { 
			{"Manager > Parts management", null},
			
			{ "Add", 		NotYetImplementedAction.class }, 
			{ "Update", 	NotYetImplementedAction.class }, 
			{ "Remove", 	NotYetImplementedAction.class }, 
			{ "List all", 	NotYetImplementedAction.class },
		};
	}

}
