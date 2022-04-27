package uo.ri.cws.application.service.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.service.util.db.UpdateQuery;

public class AddVoucherSqlUnitOfWork extends UpdateQuery<VoucherDto> {
	
	private PreparedStatement insertIntoPaymentMeans;
	private PreparedStatement insertIntoVoucher;

	public AddVoucherSqlUnitOfWork(VoucherDto dto) {
		super(dto);
	}

	@Override
	protected void prepareStatements(Connection con) throws SQLException {
		insertIntoPaymentMeans = con.prepareStatement( getInsertIntoTPaymentMeansQuery() );
		insertIntoVoucher = con.prepareStatement( getInsertIntoVouchersQuery() );
	}

	@Override
	protected void executeUpdate() throws SQLException {
		insertIntoTPaymentMeans();
		insertIntoTVouchers();
	}

	private void insertIntoTVouchers() throws SQLException {
		PreparedStatement st = insertIntoVoucher;
		bindVoucherParameters(st);
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
		st.setString(i++, "Voucher"); // DTYPE

		st.setDouble(i++, dto.accumulated);
		st.setString(i++, dto.clientId);
	}

	protected String getInsertIntoVouchersQuery() {
		return "INSERT INTO TVOUCHERS"
				+ " ( ID, CODE, DESCRIPTION, AVAILABLE )" 
				+ " VALUES ( ?, ?, ?, ?)";
	}
	protected void bindVoucherParameters(PreparedStatement st) throws SQLException {
		int i = 1;
		st.setString(i++, dto.id);
		st.setString(i++, dto.code);
		st.setString(i++, dto.description);
		st.setDouble(i++, dto.balance);
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
