package uo.ri.cws.application.service.util;

import java.util.UUID;

import uo.ri.cws.application.service.util.sql.AddRecommendationSqlUnitOfWork;

public class RecommendationUtil {

	private RecommendationDto dto = createDefaultDto();

	public class RecommendationDto {
		public String id;
		public Long version;
		
		public String sponsorId;
		public String recommendedId;
		public boolean usedForVoucher;
	}
	
	private RecommendationDto createDefaultDto() {
		RecommendationDto adto = new RecommendationDto ();
		adto.id = UUID.randomUUID().toString();
		adto.version = 1L;
		adto.usedForVoucher = false;
		return adto;
	}

	public RecommendationUtil fromSponsor(String id) {
		this.dto.sponsorId = id;
		return this;
	}

	public RecommendationUtil forClient(String id) {
		this.dto.recommendedId = id;
		return this;
	}

	public RecommendationUtil withUse(String string) {
		dto.usedForVoucher = Boolean.valueOf(string);
		return this;
	}

	public RecommendationUtil register() {
		new AddRecommendationSqlUnitOfWork( dto ).execute();
		return this;		
	}
	
	public RecommendationDto get() {
		return this.dto;
	}

}
