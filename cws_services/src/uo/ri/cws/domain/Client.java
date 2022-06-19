package uo.ri.cws.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TClients")
public class Client extends BaseEntity {

    // natural attributes
    @Column(unique = true)
    private String dni;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Address address;

    // accidental attributes
    @OneToMany(mappedBy = "client")
    Set<Vehicle> vehicles = new HashSet<>();
    @OneToMany(mappedBy = "client")
    Set<PaymentMean> paymentMeans = new HashSet<>();

    // list of recommendations of which the client is sponsor
    @OneToMany(mappedBy = "sponsor")
    Set<Recommendation> sponsored = new HashSet<>();
    // recommended by this client
    @OneToOne(mappedBy = "recommended")
    Recommendation recommended;

    Client() {
    }

    public Client(String dni) {
	ArgumentChecks.isNotEmpty(dni);

	this.dni = dni;

	this.name = "no-name";
	this.surname = "no-surname";
	this.email = "no-email";
	this.phone = "no-phone";
    }

    public Client(String dni, String name, String surname) {
	this(dni);

	ArgumentChecks.isNotEmpty(name);
	ArgumentChecks.isNotEmpty(surname);

	this.name = name;
	this.surname = surname;
    }

    protected Set<PaymentMean> _getPaymentMeans() {
	return paymentMeans;
    }

    public Set<PaymentMean> getPaymentMeans() {
	return new HashSet<>(paymentMeans);
    }

    protected Set<Vehicle> _getVehicles() {
	return vehicles;
    }

    public Set<Vehicle> getVehicles() {
	return new HashSet<>(vehicles);
    }

    public String getDni() {
	return dni;
    }

    public String getName() {
	return name;
    }

    public String getSurname() {
	return surname;
    }

    public String getEmail() {
	return email;
    }

    public String getPhone() {
	return phone;
    }

    public Address getAddress() {
	return address;
    }

    public Set<Recommendation> getSponsored() {
	return new HashSet<>(sponsored);
    }

    Set<Recommendation> _getSponsored() {
	return sponsored;
    }

    public Recommendation getRecommended() {
	return recommended;
    }

    void _setRecommended(Recommendation recommended) {
	this.recommended = recommended;
    }

    public List<WorkOrder> getWorkOrdersAvailableForVoucher() {
	List<WorkOrder> res = new ArrayList<>();
	for (Vehicle vehicle : vehicles) {
	    for (WorkOrder workOrder : vehicle.getWorkOrders()) {
		if (workOrder.canBeUsedForVoucher()) {
		    res.add(workOrder);
		}
	    }
	}
	return res;
    }

    @Override
    public String toString() {
	return "Client [dni=" + dni + ", name=" + name + ", surname=" + surname
		+ ", email=" + email + ", phone=" + phone + ", address="
		+ address + "]";
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setSurname(String surname) {
	this.surname = surname;
    }

    public boolean eligibleForRecommendationVoucher() {
	return hasPaidWorkOrder() && sponsored.stream()
		.filter(recommendation -> recommendation.isValidForVoucher())
		.collect(Collectors.toList()).size() >= 3;
    }

    public boolean hasPaidWorkOrder() {
	for (Vehicle vehicle : vehicles) {
	    for (WorkOrder wo : vehicle.getWorkOrders()) {
		if (wo.isInvoiced() && wo.getInvoice().isSettled())
		    return true;
	    }
	}
	return false;
    }

    public List<Recommendation> getRecommendationsValidForVoucher() {
	return sponsored.stream()
		.filter(recommendation -> recommendation.isValidForVoucher())
		.collect(Collectors.toList());
    }

}
