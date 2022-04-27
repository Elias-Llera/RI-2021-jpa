package uo.ri.cws.application.service.util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.service.util.db.OneParameterQuery;

public class FindClientByDniSqlUnitOfWork extends OneParameterQuery<String, ClientDto>{

	public FindClientByDniSqlUnitOfWork(String dni) {
		super( dni );
	}

	@Override
	protected ClientDto parseRow(ResultSet rs) throws SQLException {
		ClientDto res = new ClientDto();
		res.id = rs.getString("id");
		res.version = rs.getLong("version");
		res.dni = rs.getString("dni");
		res.name = rs.getString("name");
		res.surname = rs.getString("surname");
		res.phone = rs.getString("phone");
		res.email = rs.getString("email");
		res.addressCity = rs.getString("city");
		res.addressStreet = rs.getString("street");
		res.addressZipcode = rs.getString("zipcode");
		return res;
	}

	@Override
	protected String getSqlQuery() {
		return "SELECT * FROM TCLIENTS WHERE DNI = ?";
	}

}
