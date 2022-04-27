package uo.ri.cws.application.service.util.sql;

public class FindCashByClientIdSqlUnitOfWork extends FindCashBy {

	public FindCashByClientIdSqlUnitOfWork(String id) {
		super( id );
	}

	@Override
	protected String getSqlQuery() {
		return "SELECT *"
					+ " FROM TCASHES tc"
					+ " 	inner join TPAYMENTMEANS pm on tc.id = pm.id"
					+ " WHERE pm.CLIENT_ID = ?";
	}

}
