package uo.ri.cws.application.service.util.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uo.ri.cws.application.service.util.RecommendationUtil.RecommendationDto;
import uo.ri.cws.application.service.util.db.UpdateQuery;

public class AddRecommendationSqlUnitOfWork extends UpdateQuery<RecommendationDto>{

	public AddRecommendationSqlUnitOfWork(RecommendationDto dto) {
		super( dto );
	}

	@Override
	protected String getUpdateQuery() {
		return "INSERT INTO TRECOMMENDATIONS"
				+ " ( ID, VERSION, SPONSOR_ID, RECOMMENDED_ID, USEDFORVOUCHER )"
				+ " VALUES ( ?, ?, ?, ?, ? )";
	}

	@Override
	protected void bindParameters(PreparedStatement st) throws SQLException {
		int i = 1;
		st.setString(i++, dto.id);
		st.setLong(i++, dto.version);
		st.setString(i++, dto.sponsorId);
		st.setString(i++, dto.recommendedId);
		st.setBoolean(i++, dto.usedForVoucher);
	}
}
