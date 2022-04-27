package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TSubstitutions", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "SPAREPART_ID",
				"INTERVENTION_ID" }) })
public class Substitution extends BaseEntity {
	// natural attributes
	private int quantity;

	// accidental attributes
	@ManyToOne
	private SparePart sparePart;
	@ManyToOne
	private Intervention intervention;

	Substitution() {
	}

	public Substitution(SparePart sp, Intervention intervention) {
		ArgumentChecks.isNotNull(intervention);
		ArgumentChecks.isNotNull(sp);

		Associations.Sustitute.link(sp, this, intervention);
	}

	public Substitution(SparePart sparePart, Intervention intervention,
			int quantity) {
		this(sparePart, intervention);

		ArgumentChecks.isTrue(quantity >= 1);

		this.quantity = quantity;
	}

	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public int getQuantity() {
		return quantity;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	@Override
	public String toString() {
		return "Substitution [quantity=" + quantity + ", sparePart=" + sparePart
				+ ", intervention=" + intervention + "]";
	}

	public double getAmount() {
		return sparePart.getPrice() * quantity;
	}

}
