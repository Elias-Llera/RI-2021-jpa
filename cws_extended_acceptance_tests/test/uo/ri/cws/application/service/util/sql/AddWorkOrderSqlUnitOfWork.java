package uo.ri.cws.application.service.util.sql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import uo.ri.cws.application.service.util.db.UpdateQuery;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;

public class AddWorkOrderSqlUnitOfWork extends UpdateQuery<WorkOrderDto> {

	public AddWorkOrderSqlUnitOfWork(WorkOrderDto dto) {
		super( dto );
	}

	@Override
	protected String getUpdateQuery() {
		return "INSERT INTO TWORKORDERS ("
				+ " ID, AMOUNT, DATE, DESCRIPTION, STATUS, USEDFORVOUCHER, "
				+ " VERSION, VEHICLE_ID, MECHANIC_ID, INVOICE_ID"
				+ " )"
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	protected void bindParameters(PreparedStatement st) throws SQLException {
		int i = 1;
		st.setString(i++, dto.id);
		st.setDouble(i++, dto.total);

		st.setDate(i++, Date.valueOf(dto.date.toLocalDate()));
		st.setString(i++, dto.description);
		st.setString(i++, dto.status);
		st.setBoolean(i++, dto.usedForVoucher);
		st.setLong(i++, dto.version);
		st.setString(i++, dto.vehicleId);
		st.setString(i++, dto.mechanicId);
		st.setString(i++, dto.invoiceId);
	}

}
