package uo.ri.cws.application.service.util.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.util.db.UpdateQuery;

public class AddMechanicSqlUnitOfWork extends UpdateQuery<MechanicDto> {

	public AddMechanicSqlUnitOfWork(MechanicDto dto) {
		super( dto );
	}

	@Override
	protected void bindParameters(PreparedStatement st) throws SQLException {
		int i = 1;
		st.setString(i++, dto.id);
		st.setString(i++, dto.dni);
		st.setString(i++, dto.name);
		st.setString(i++, dto.surname);
		st.setLong(i++, dto.version);
	}

	@Override
	protected String getUpdateQuery() {
		return "INSERT INTO TMECHANICS"
				+ " ( ID, DNI, NAME, SURNAME, VERSION )"
				+ " VALUES ( ?, ?, ?, ?, ?)";
	}

}
