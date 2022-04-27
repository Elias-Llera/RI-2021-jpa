package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TRecommendations")
public class Recommendation extends BaseEntity {

	// natural attributes
	private boolean usedForVoucher = false;

	// accidental attributes
	@ManyToOne
	private Client sponsor;
	@OneToOne
	private Client recommended;

	public Recommendation() {
	}

	public Recommendation(Client sponsor, Client rec) {
		ArgumentChecks.isNotNull(sponsor);
		ArgumentChecks.isNotNull(rec);
		Associations.Recommend.link(this, sponsor, rec);
	}

	public Recommendation(Client sponsor, Client rec, boolean usedForVoucher) {
		this(sponsor, rec);
		this.usedForVoucher = usedForVoucher;
	}

	public boolean isUsedForVoucher() {
		return usedForVoucher;
	}

	public Client getSponsor() {
		return sponsor;
	}

	public Client getRecommended() {
		return recommended;
	}

	void _setSponsor(Client sponsor) {
		this.sponsor = sponsor;
	}

	void _setRecommended(Client recommended) {
		this.recommended = recommended;
	}

	public void markAsUsed() {
		this.usedForVoucher = true;
	}

	@Override
	public String toString() {
		return "Recommendation [usedForVoucher=" + usedForVoucher + ", sponsor="
				+ sponsor + ", recommended=" + recommended + "]";
	}

	public boolean isValidForVoucher() {
		return !usedForVoucher && recommended.hasPaidWorkOrder();
	}
	
	public void markAsUsedForVoucher() {
		usedForVoucher = true;
	}

}