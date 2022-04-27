package uo.ri.cws.application.service.util.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CardDto;
import uo.ri.cws.application.service.util.db.UpdateQuery;

public class AddCreditCardSqlUnitOfWork extends UpdateQuery<CardDto> {
	
	private PreparedStatement insertIntoPaymentMeans;
	private PreparedStatement insertIntoCreditCards;

	public AddCreditCardSqlUnitOfWork(CardDto dto) {
		super(dto);
	}

	@Override
	protected void prepareStatements(Connection con) throws SQLException {
		insertIntoPaymentMeans = con.prepareStatement( getInsertIntoTPaymentMeansQuery() );
		insertIntoCreditCards = con.prepareStatement( getInsertIntoCreditCardsQuery() );
	}

	@Override
	protected void executeUpdate() throws SQLException {
		insertIntoTPaymentMeans();
		insertIntoTCreditCards();
	}

	private void insertIntoTCreditCards() throws SQLException {
		PreparedStatement st = insertIntoCreditCards;
		bindCreditCardParameters(st);
		st.executeUpdate();
	}

	private void insertIntoTPaymentMeans() throws SQLException {
		PreparedStatement st = insertIntoPaymentMeans;
		bindPaymentMeanParameters(st);
		st.executeUpdate();
	}

	private String getInsertIntoTPaymentMeansQuery() {
		return "INSERT INTO TPAYMENTMEANS"
				+ " ( ID, VERSION, DTYPE, ACCUMULATED, CLIENT_ID )"
				+ " VALUES ( ?, ?, ?, ?, ?)";
	}

	private void bindPaymentMeanParameters(PreparedStatement st) throws SQLException {
		int i = 1;
		st.setString(i++, dto.id);
		st.setLong(i++, dto.version);
		st.setString(i++, "CreditCard"); // DTYPE

		st.setDouble(i++, dto.accumulated);
		st.setString(i++, dto.clientId);
	}

	protected String getInsertIntoCreditCardsQuery() {
		return "INSERT INTO TCREDITCARDS"
				+ " ( ID, NUMBER, TYPE, VALIDTHRU )" 
				+ " VALUES ( ?, ?, ?, ?)";
	}
	protected void bindCreditCardParameters(PreparedStatement st) throws SQLException {
		int i = 1;
		st.setString(i++, dto.id);
		st.setString(i++, dto.cardNumber);
		st.setString(i++, dto.cardType);
		st.setDate(i++, Date.valueOf(dto.cardExpiration));
	}

	@Override
	protected void bindParameters(PreparedStatement st) throws SQLException {
		// empty on purpose, not been called as execute() method is overridden		
	}

	@Override
	protected String getUpdateQuery() {
		// empty on purpose, not been called as execute() method is overridden		
		return null;
	}
}
