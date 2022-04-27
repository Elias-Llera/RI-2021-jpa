package uo.ri.ui.cashier.paymentmeans;

import alb.util.menu.BaseMenu;
import uo.ri.ui.cashier.paymentmeans.action.AddPaymentMeanAction;
import uo.ri.ui.cashier.paymentmeans.action.DeletePaymentMeanAction;
import uo.ri.ui.cashier.paymentmeans.action.ListPaymentMeansByClientAction;

public class PaymentMeansMenu extends BaseMenu {{
	menuOptions = new Object[][] { 
		{ "Foreman > Payment means management", null },

		{ "Add payment mean", 				AddPaymentMeanAction.class }, 
		{ "Delete payment mean", 			DeletePaymentMeanAction.class }, 
		{ "List payment means of a client", ListPaymentMeansByClientAction.class }, 
	};
}}
