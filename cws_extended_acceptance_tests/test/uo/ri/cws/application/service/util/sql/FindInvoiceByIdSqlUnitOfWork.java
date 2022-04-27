package uo.ri.cws.application.service.util.sql;

public class FindInvoiceByIdSqlUnitOfWork extends FindInvoiceBy<String> {

    public FindInvoiceByIdSqlUnitOfWork(String id) {
    	super( id );
    }

	@Override
	protected String getSqlQuery() {
		return "SELECT * FROM TINVOICES WHERE iD = ?";
	}

}
