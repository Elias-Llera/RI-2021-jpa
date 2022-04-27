package uo.ri.cws.application.service.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @author alb
 *
 * @param <K> The type of the key parameter to find for
 * @param <T> The type returned by the query
 */
public abstract class OneParameterQuery<K, T> {

	private ConnectionData connectionData = PersistenceXmlScanner.scan();
	private PreparedStatement sqlStatement;
	
	protected K key;
	private List<T> res = new ArrayList<>();

	public OneParameterQuery(K parameter) {
		this.key = parameter;
	}

	public OneParameterQuery<K, T> execute() {
		JdbcTransaction trx = new JdbcTransaction(connectionData);
		trx.execute((con) -> {
			prepareStatements(con);
			executeQuery();
		});
		return this;
	}

	protected abstract T parseRow(ResultSet rs) throws SQLException;

	protected abstract String getSqlQuery();

	public Optional<T> get() {
		return res.stream().findFirst();
	}

	public List<T> getAll() {
		return res;
	}

	private void executeQuery() throws SQLException {
		PreparedStatement st = sqlStatement;
		st.setObject(1, key);  // polymorphic
	
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			res.add( parseRow(rs) );
		}
	}

	private void prepareStatements(Connection con) throws SQLException {
		sqlStatement = con.prepareStatement( getSqlQuery() );
	}

}