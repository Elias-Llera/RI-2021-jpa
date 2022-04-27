package uo.ri.cws.application.service.util.sql;

public class FindCardByIdSqlUnitOfWork extends FindCardBy {

	@Override
	protected String getSqlQuery() {
		return "SELECT *"
			+ " FROM TCREDITCARDS tc"
			+ " 	inner join TPaymentMeans tpm on tc.id = tpm.id"
			+ " WHERE tc.id = ?";
	}

	public FindCardByIdSqlUnitOfWork(String id) {
		super(id);
	}

}
