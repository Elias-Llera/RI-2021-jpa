package uo.ri.cws.application.service.util.sql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.util.db.UpdateQuery;

public class AddInvoiceSqlUnitOfWork extends UpdateQuery<InvoiceDto> {

	public AddInvoiceSqlUnitOfWork(InvoiceDto dto) {
		super(dto);
	}

	@Override
	protected void bindParameters(PreparedStatement st) throws SQLException {
		int i = 1;
		st.setString(i++, dto.id);
		st.setLong(i++, dto.version);
		st.setLong(i++, dto.number);
		st.setDouble(i++, dto.total);
		st.setDouble(i++, dto.vat);
		st.setDate(i++, Date.valueOf(dto.date));
		st.setString(i++, dto.status);
		st.setBoolean(i++, dto.usedForVoucher);
	}

	@Override
	protected String getUpdateQuery() {
		return "INSERT INTO TINVOICES"
				+ " ( ID, VERSION, NUMBER, AMOUNT, VAT, DATE, STATUS, USEDFORVOUCHER )"
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
	}
}
