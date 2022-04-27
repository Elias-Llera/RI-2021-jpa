package uo.ri.cws.application.service.util.sql;

public class FindVouchersByClientIdSqlUnitOfWork extends FindVoucherBy {

	public FindVouchersByClientIdSqlUnitOfWork(String code) {
		super(code);
	}

	@Override
	protected String getSqlQuery() {
		return "SELECT *"
				+ " FROM TVOUCHERS tv "
				+ "		inner join TPaymentMeans tpm on tv.id = tpm.id"
				+ " where tpm.client_id = ? ";
	}

}
