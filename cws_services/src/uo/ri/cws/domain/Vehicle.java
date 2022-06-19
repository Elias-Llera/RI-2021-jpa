package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TVehicles")
public class Vehicle extends BaseEntity {
    // natural attributes
    @Column(unique = true)
    private String plateNumber;
    private String make;
    private String model;

    // accidental attributes
    @ManyToOne
    private Client client;
    @ManyToOne
    private VehicleType vehicleType;
    @OneToMany(mappedBy = "vehicle")
    private Set<WorkOrder> workOrders = new HashSet<>();

    Vehicle() {
    }

    public Vehicle(String plateNumber) {
	ArgumentChecks.isNotEmpty(plateNumber);

	this.plateNumber = plateNumber;
    }

    public Vehicle(String plate, String make, String model) {
	this(plate);

	ArgumentChecks.isNotEmpty(make);
	ArgumentChecks.isNotEmpty(model);

	this.make = make;
	this.model = model;
    }

    public Set<WorkOrder> getWorkOrders() {
	return new HashSet<>(workOrders);
    }

    protected Set<WorkOrder> _getWorkOrders() {
	return workOrders;
    }

    protected void _setVehicleType(VehicleType vehicleType) {
	this.vehicleType = vehicleType;
    }

    public VehicleType getVehicleType() {
	return vehicleType;
    }

    protected void _setClient(Client client) {
	this.client = client;
    }

    public Client getClient() {
	return client;
    }

    public String getPlateNumber() {
	return plateNumber;
    }

    public String getMake() {
	return make;
    }

    public String getModel() {
	return model;
    }

    @Override
    public String toString() {
	return "Vehicle [plateNumber=" + plateNumber + ", make=" + make
		+ ", model=" + model + "]";
    }

}
