package uo.ri.cws.application.service.util.sql;

public class FindCardByNumberSqlUnitOfWork extends FindCardBy {

	public FindCardByNumberSqlUnitOfWork(String cardNumber) {
		super(cardNumber);
	}

	@Override
	protected String getSqlQuery() {		
		return "SELECT *" 
				+ " FROM TCREDITCARDS tc"
				+ " 	inner join TPaymentMeans tpm on tc.id = tpm.id"
				+ " WHERE tc.NUMBER = ?";
	}

}
