package uo.ri.cws.application.service.util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.util.db.OneParameterQuery;

public class FindMechanicByIdSqlUnitOfWork extends OneParameterQuery<String, MechanicDto> {

	public FindMechanicByIdSqlUnitOfWork(String parameter) {
		super(parameter);
	}

	@Override
	protected MechanicDto parseRow(ResultSet rs) throws SQLException {
		MechanicDto dto = new MechanicDto();
		dto.id = rs.getString("id");
		dto.version = rs.getLong("version");
		dto.dni = rs.getString("dni");
		dto.name = rs.getString("name");
		dto.surname = rs.getString("surname");
		return dto;
	}

	@Override
	protected String getSqlQuery() {
		return "select * from TMechanics where id = ?";
	}

}
