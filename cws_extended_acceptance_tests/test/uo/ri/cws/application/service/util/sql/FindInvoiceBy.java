package uo.ri.cws.application.service.util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.util.db.OneParameterQuery;

public abstract class FindInvoiceBy<K> extends OneParameterQuery<K, InvoiceDto> {

	public FindInvoiceBy(K id) {
		super( id );
	}

	@Override
	protected InvoiceDto parseRow(ResultSet rs) throws SQLException {
		InvoiceDto res = new InvoiceDto();
	    res.id = rs.getString("id");
	    res.version = rs.getLong("version");
	
	    res.number = rs.getLong("number");
	    res.status = rs.getString("status");
	    res.date = rs.getDate("date").toLocalDate();
	    res.total = rs.getDouble("amount");
	    res.vat = rs.getDouble("vat");
	    res.usedForVoucher = rs.getBoolean("usedForVoucher");
	    return res;
	}

}
