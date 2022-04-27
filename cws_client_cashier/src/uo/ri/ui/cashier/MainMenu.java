package uo.ri.ui.cashier;

import alb.util.menu.BaseMenu;
import uo.ri.ui.cashier.action.FindNotInvoicedWorkOrders;
import uo.ri.ui.cashier.action.FindWorOrdersByPlateAction;
import uo.ri.ui.cashier.action.FindWorkOrdersByClientAction;
import uo.ri.ui.cashier.action.InvoiceWorkOrderAction;
import uo.ri.ui.cashier.action.SettleInvoiceAction;
import uo.ri.ui.cashier.paymentmeans.PaymentMeansMenu;

public class MainMenu extends BaseMenu {{
		menuOptions = new Object[][] { 
			{ "Cash", null },
			{ "Payment Mean management",		PaymentMeansMenu.class },
			{ "Find work orders by client", 	FindWorkOrdersByClientAction.class },
			{ "Find work orders by plate", 		FindWorOrdersByPlateAction.class },
			{ "Find not invoiced work orders", 	FindNotInvoicedWorkOrders.class },
			{ "Invoice work orders", 			InvoiceWorkOrderAction.class },
			{ "Settle invoice", 				SettleInvoiceAction.class },
		};
}}