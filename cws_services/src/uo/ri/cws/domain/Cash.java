package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TCashes")
public class Cash extends PaymentMean {

    Cash() {
    }

    public Cash(Client client) {
	Associations.Pay.link(client, this);
    }

    @Override
    public String toString() {
	return "Cash [getAccumulated()=" + getAccumulated() + ", getClient()="
		+ getClient() + "]";
    }

    @Override
    public boolean canPay(double amount) {
	return true;
    }

}
