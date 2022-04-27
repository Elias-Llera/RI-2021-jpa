package uo.ri.cws.application.service.util.sql;

public class FindInvoiceByNumberSqlUnitOfWork extends FindInvoiceBy<Long> {

	public FindInvoiceByNumberSqlUnitOfWork(Long number) {
		super( number );
	}

	@Override
	protected String getSqlQuery() {
		return "SELECT * FROM TINVOICES WHERE number = ?";
	}
	
}
