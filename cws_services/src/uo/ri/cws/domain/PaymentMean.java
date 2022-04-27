package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TPaymentMeans")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PaymentMean extends BaseEntity {
	// natural attributes
	private double accumulated = 0.0;

	// accidental attributes
	@ManyToOne
	private Client client;
	@OneToMany(mappedBy = "paymentMean")
	private Set<Charge> charges = new HashSet<>();

	public void pay(double importe) {
		this.accumulated += importe;
	}

	void _setClient(Client client) {
		this.client = client;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	public double getAccumulated() {
		return accumulated;
	}

	public Client getClient() {
		return client;
	}
	
	public abstract boolean canPay(double amount);

	@Override
	public String toString() {
		return "PaymentMean [accumulated=" + accumulated + ", client=" + client
				+ "]";
	}
	
}
