package uo.ri.cws.application.service.util.sql;

public class FindVoucherByIdSqlUnitOfWork extends FindVoucherBy {

	public FindVoucherByIdSqlUnitOfWork(String id) {
		super(id);
	}

	@Override
	protected String getSqlQuery() {
		return "SELECT *"
				+ " FROM TVOUCHERS tv "
				+ "		inner join TPaymentMeans tpm on tv.id = tpm.id"
				+ " where tv.id = ? ";
	}
}
