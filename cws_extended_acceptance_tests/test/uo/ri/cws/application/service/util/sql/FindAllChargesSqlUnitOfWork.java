package uo.ri.cws.application.service.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.service.invoice.InvoicingService.ChargeDto;
import uo.ri.cws.application.service.util.db.ConnectionData;
import uo.ri.cws.application.service.util.db.JdbcTransaction;
import uo.ri.cws.application.service.util.db.PersistenceXmlScanner;

public class FindAllChargesSqlUnitOfWork {

	private ConnectionData connectionData;
	private PreparedStatement findAllCharges;
	private List<ChargeDto> charges = new ArrayList<ChargeDto>();

	public FindAllChargesSqlUnitOfWork() {
		this.connectionData = PersistenceXmlScanner.scan();
	}

	public void execute() {
		JdbcTransaction trx = new JdbcTransaction(connectionData);
		trx.execute((con) -> {
			prepareStatements(con);
			findAllCharges();
		});
	}

	public List<ChargeDto> get() {
		return this.charges;
	}

	private static final String FIND_ALL_CHARGES = "SELECT * FROM TCHARGES ";

	private void findAllCharges() throws SQLException {
		PreparedStatement st = findAllCharges;
		ResultSet rs = st.executeQuery();
		ChargeDto item;
		while (rs.next()) {
			item = new ChargeDto();
			item.id = rs.getString("id");
			item.invoice_id = rs.getString("invoice_id");
			item.paymentMean_id = rs.getString("paymentMean_id");
			item.amount = rs.getDouble("amount");
			this.charges.add(item);
		}
	}

	private void prepareStatements(Connection con) throws SQLException {
		findAllCharges = con.prepareStatement(FIND_ALL_CHARGES);
	}

}
