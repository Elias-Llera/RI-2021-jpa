package uo.ri.cws.application.service.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class UpdateQuery<T> {

	private ConnectionData connectionData = PersistenceXmlScanner.scan();
	private PreparedStatement updateStatement;

	protected T dto;

	public UpdateQuery(T dto) {
		this.dto = dto;
	}
	
	protected abstract void bindParameters(PreparedStatement st) throws SQLException;
	protected abstract String getUpdateQuery();

	public void execute() {
		JdbcTransaction trx = new JdbcTransaction(connectionData);
		trx.execute((con) -> {
			prepareStatements(con);
			executeUpdate();
		});
	}

	protected void executeUpdate() throws SQLException {
		PreparedStatement st = updateStatement;
		bindParameters(st);
		st.executeUpdate();
	}

	protected void prepareStatements(Connection con) throws SQLException {
		updateStatement = con.prepareStatement( getUpdateQuery() );
	}

}
