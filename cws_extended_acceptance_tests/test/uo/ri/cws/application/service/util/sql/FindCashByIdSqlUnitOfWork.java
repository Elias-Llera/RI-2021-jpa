package uo.ri.cws.application.service.util.sql;

public class FindCashByIdSqlUnitOfWork extends FindCashBy{

	public FindCashByIdSqlUnitOfWork(String id) {
		super( id );
	}

	@Override
	protected String getSqlQuery() {
		return "SELECT *"
					+ " FROM TCASHES tc"
					+ " 	inner join TPAYMENTMEANS tpm on tc.id = tpm.id"
					+ " WHERE tc.ID = ?";
	}

}
