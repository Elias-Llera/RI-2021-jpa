package uo.ri.cws.application.service.util.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uo.ri.cws.application.service.invoice.InvoicingService.ChargeDto;
import uo.ri.cws.application.service.util.db.UpdateQuery;

public class AddChargeSqlUnitOfWork extends UpdateQuery<ChargeDto> {

	public AddChargeSqlUnitOfWork(ChargeDto dto) {
		super( dto );
	}

	@Override
	protected void bindParameters(PreparedStatement st) throws SQLException {
		int i = 1;
		st.setString(i++, dto.id);
		st.setDouble(i++, dto.amount);
		st.setString(i++, dto.invoice_id);
		st.setString(i++, dto.paymentMean_id);
		st.setLong(i++, dto.version);
	}

	@Override
	protected String getUpdateQuery() {
		return "INSERT INTO TCHARGES"
				+ " ( ID, AMOUNT, INVOICE_ID, PAYMENTMEAN_ID, VERSION )"
				+ " VALUES ( ?, ?, ?, ?, ?)";
	}

}
