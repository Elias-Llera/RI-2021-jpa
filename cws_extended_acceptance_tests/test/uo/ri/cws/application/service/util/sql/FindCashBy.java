package uo.ri.cws.application.service.util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CashDto;
import uo.ri.cws.application.service.util.db.OneParameterQuery;

public abstract class FindCashBy extends OneParameterQuery<String, CashDto>{

	public FindCashBy(String id) {
		super( id );
	}

	@Override
	protected CashDto parseRow(ResultSet rs) throws SQLException {
		CashDto res = new CashDto();
		res.id = rs.getString("id");
		res.clientId = rs.getString("client_id");
		res.accumulated = rs.getDouble("accumulated");
		return res;
	}

}
