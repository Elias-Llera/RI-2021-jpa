package uo.ri.ui.manager.voucher;

import alb.util.menu.BaseMenu;
import uo.ri.ui.manager.voucher.action.AggregateVocherListAction;
import uo.ri.ui.manager.voucher.action.ClientVoucherListAction;
import uo.ri.ui.manager.voucher.action.GenerateVoucherAction;

public class VoucherMenu extends BaseMenu {

	public VoucherMenu() {
		menuOptions = new Object[][] { { "Administrador > Gestión de bonos", null },

				{ "Automatic voucher generation", GenerateVoucherAction.class },
				{ "List vouchers of a client", ClientVoucherListAction.class },
				{ "Voucher summary listing", AggregateVocherListAction.class }, };

	}
}
