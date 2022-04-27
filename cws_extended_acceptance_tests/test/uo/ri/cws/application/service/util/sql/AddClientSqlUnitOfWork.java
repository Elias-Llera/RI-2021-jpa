package uo.ri.cws.application.service.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CashDto;
import uo.ri.cws.application.service.util.db.ConnectionData;
import uo.ri.cws.application.service.util.db.JdbcTransaction;
import uo.ri.cws.application.service.util.db.PersistenceXmlScanner;

public class AddClientSqlUnitOfWork {

	private ClientDto dto;
	private CashDto cash;
	private ConnectionData connectionData;
	private PreparedStatement insertIntoClients;
	private PreparedStatement insertIntoPaymentMeans;
	private PreparedStatement insertIntoCashes;

	public AddClientSqlUnitOfWork(ClientDto dto) {
		this.connectionData = PersistenceXmlScanner.scan();
		this.dto = dto;
	}

	public void execute() {
		JdbcTransaction trx = new JdbcTransaction(connectionData);
		trx.execute((con) -> {
			prepareStatements(con);
			insertClient();
			insertCashPaymentMean();
		});
	}

	private void insertCashPaymentMean() throws SQLException {
		cash = new CashDto();

		cash.id = UUID.randomUUID().toString();
		cash.version = 1L;
		cash.clientId = dto.id;
		cash.accumulated = 0.0;

		insertPaymentMean();
		insertCash();
	}

	private static final String INSERT_INTO_TCASH = 
			"INSERT INTO TCASHES ( ID ) VALUES (?)";

	private void insertCash() throws SQLException {
		PreparedStatement st = insertIntoCashes;
		int i = 1;
		st.setString(i++, cash.id);

		st.executeUpdate();

	}

	private static final String INSERT_INTO_TPAYMENTMEAN = "INSERT INTO TPaymentMeans"
			+ " ( ID, DTYPE, ACCUMULATED, CLIENT_ID, VERSION )" + " VALUES ( ?, ?, ?, ?, ?)";

	private void insertPaymentMean() throws SQLException {
		PreparedStatement st = insertIntoPaymentMeans;
		int i = 1;
		st.setString(i++, cash.id);
		st.setString(i++, "Cash");
		st.setDouble(i++, cash.accumulated);
		st.setString(i++, cash.clientId);
		st.setLong(i++, dto.version);

		st.executeUpdate();

	}

	private static final String INSERT_INTO_TCLIENTS = "INSERT INTO TCLIENTS"
			+ " ( ID, DNI, NAME, SURNAME, EMAIL, PHONE, CITY, STREET, ZIPCODE, VERSION )"
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private void insertClient() throws SQLException {
		PreparedStatement st = insertIntoClients;
		int i = 1;
		st.setString(i++, dto.id);
		st.setString(i++, dto.dni);
		st.setString(i++, dto.name);
		st.setString(i++, dto.surname);
		st.setString(i++, dto.email);
		st.setString(i++, dto.phone);
		st.setString(i++, dto.addressCity);
		st.setString(i++, dto.addressStreet);
		st.setString(i++, dto.addressZipcode);
		st.setLong(i++, dto.version);

		st.executeUpdate();
	}

	private void prepareStatements(Connection con) throws SQLException {
		insertIntoClients = con.prepareStatement(INSERT_INTO_TCLIENTS);
		insertIntoPaymentMeans = con.prepareStatement(INSERT_INTO_TPAYMENTMEAN);
		insertIntoCashes = con.prepareStatement(INSERT_INTO_TCASH);
	}

}
