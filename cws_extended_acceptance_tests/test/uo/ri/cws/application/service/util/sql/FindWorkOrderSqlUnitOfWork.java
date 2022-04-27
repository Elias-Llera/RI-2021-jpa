package uo.ri.cws.application.service.util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.service.util.db.OneParameterQuery;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;

public class FindWorkOrderSqlUnitOfWork extends OneParameterQuery<String, WorkOrderDto>{

    public FindWorkOrderSqlUnitOfWork(String id) {
    	super( id );
    }

    @Override
	protected WorkOrderDto parseRow(ResultSet rs) throws SQLException {
		WorkOrderDto res = new WorkOrderDto();
	    res.id = rs.getString("id");
	    res.version = rs.getLong("version");
	    res.total = rs.getDouble("amount");
	    res.invoiceId = rs.getString("invoice_id");
	    res.status = rs.getString("status");
	    res.date = rs.getTimestamp("date").toLocalDateTime();
	    res.description = rs.getString("description");
	    res.mechanicId = rs.getString("mechanic_id");
	    res.vehicleId = rs.getString("vehicle_id");
	    res.usedForVoucher = rs.getBoolean("usedForVoucher");
	    return res;
	}
	@Override
	protected String getSqlQuery() {
		return "SELECT * FROM TWORKORDERS WHERE id = ?";
	}

}
