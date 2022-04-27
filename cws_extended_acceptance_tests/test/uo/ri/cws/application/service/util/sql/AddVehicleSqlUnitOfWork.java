package uo.ri.cws.application.service.util.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uo.ri.cws.application.service.util.db.UpdateQuery;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;

public class AddVehicleSqlUnitOfWork extends UpdateQuery<VehicleDto> {

	public AddVehicleSqlUnitOfWork(VehicleDto dto) {
		super( dto );
	}

	@Override
	protected void bindParameters(PreparedStatement st) throws SQLException {
		int i = 1;
		st.setString(i++, dto.id);
		st.setString(i++, dto.plate);
		st.setString(i++, dto.make);
		st.setString(i++, dto.model);
		st.setString(i++, dto.clientId);
		st.setLong(i++, dto.version);
	}

	@Override
	protected String getUpdateQuery() {
		return 	"INSERT INTO TVEHICLES"
				+ " ( ID, PLATENUMBER, MAKE, MODEL, CLIENT_ID, VERSION )"
				+ " VALUES ( ?, ?, ?, ?, ?, ?)";
	}

}
