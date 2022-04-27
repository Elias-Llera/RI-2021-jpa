package uo.ri.cws.application.service.util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.service.util.db.OneParameterQuery;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;

public class FindVehicleByPlateSqlUnitOfWork extends OneParameterQuery<String, VehicleDto> {

	public FindVehicleByPlateSqlUnitOfWork(String plate) {
		super(plate);
	}

	@Override
	protected String getSqlQuery() {
		return "SELECT * FROM TVEHICLES WHERE PLATENUMBER = ?";
	}

	@Override
	protected VehicleDto parseRow(ResultSet rs) throws SQLException {
		VehicleDto res = new VehicleDto();
		res.id = rs.getString("id");
		res.version = rs.getLong("version");
		res.clientId = rs.getString("client_id");
		res.make = rs.getString("make");
		res.model = rs.getString("model");
		res.plate = rs.getString("plateNumber");
		res.vehicleTypeId = rs.getString("vehicleType_id");
		return res;
	}

}
