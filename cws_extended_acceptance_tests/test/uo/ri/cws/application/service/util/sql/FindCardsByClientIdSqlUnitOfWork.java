package uo.ri.cws.application.service.util.sql;

public class FindCardsByClientIdSqlUnitOfWork extends FindCardBy {

	@Override
	protected String getSqlQuery() {
		
		return "SELECT *" 
				+ " FROM TCREDITCARDS tc"
				+ " 	inner join TPaymentMeans tpm on tc.id = tpm.id"
				+ " WHERE tpm.client_id = ?";
	}

	public FindCardsByClientIdSqlUnitOfWork(String clientId) {
		super(clientId);
	}

}
