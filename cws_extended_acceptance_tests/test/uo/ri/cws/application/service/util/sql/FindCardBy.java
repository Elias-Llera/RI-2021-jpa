package uo.ri.cws.application.service.util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CardDto;
import uo.ri.cws.application.service.util.db.OneParameterQuery;

public abstract class FindCardBy extends OneParameterQuery<String, CardDto> {

	public FindCardBy(String key) {
		super(key);
	}

	@Override
	protected CardDto parseRow(ResultSet rs) throws SQLException {
		CardDto res = new CardDto();
		res.id = rs.getString("id");
		res.version = rs.getLong("version");
		
		res.clientId = rs.getString("client_id");
		res.cardType = rs.getString("type");
		res.cardNumber = rs.getString("number");
		res.cardExpiration = rs.getDate("validthru").toLocalDate();
		res.accumulated = rs.getDouble("accumulated");
		res.version = rs.getLong("version");
		return res;
	}
}