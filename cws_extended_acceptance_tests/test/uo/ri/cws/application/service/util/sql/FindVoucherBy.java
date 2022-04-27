package uo.ri.cws.application.service.util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.service.util.db.OneParameterQuery;

public abstract class FindVoucherBy extends OneParameterQuery<String, VoucherDto> {

	public FindVoucherBy(String key) {
		super(key);
	}

	@Override
	protected VoucherDto parseRow(ResultSet rs) throws SQLException {
		VoucherDto res = new VoucherDto();
		res.id = rs.getString("id");
		res.version = rs.getLong("version");
		res.code = rs.getString("code");
		res.description = rs.getString("description");
		res.accumulated = rs.getDouble("accumulated");
		res.balance = rs.getDouble("available");
		res.clientId = rs.getString("client_id");
		return res;
	}
}