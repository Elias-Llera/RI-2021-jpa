package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TMechanics")
public class Mechanic extends BaseEntity {
    // natural attributes
    @Column(unique = true)
    private String dni;
    private String surname;
    private String name;

    // accidental attributes
    @OneToMany(mappedBy = "mechanic")
    private Set<WorkOrder> assigned = new HashSet<>();
    @OneToMany(mappedBy = "mechanic")
    private Set<Intervention> interventions = new HashSet<>();

    Mechanic() {
    }

    public Mechanic(String dni) {
	ArgumentChecks.isNotNull(dni);
	ArgumentChecks.isNotEmpty(dni);

	this.dni = dni;

	this.name = "no-name";
	this.surname = "surname";
    }

    public Mechanic(String dni, String name, String surname) {
	this(dni);

	ArgumentChecks.isNotEmpty(name);
	ArgumentChecks.isNotEmpty(surname);

	this.name = name;
	this.surname = surname;
    }

    public Set<WorkOrder> getAssigned() {
	return new HashSet<>(assigned);
    }

    Set<WorkOrder> _getAssigned() {
	return assigned;
    }

    public Set<Intervention> getInterventions() {
	return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
	return interventions;
    }

    public String getDni() {
	return dni;
    }

    public void setSurname(String surname) {
	this.surname = surname;
    }

    public String getSurname() {
	return surname;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    @Override
    public String toString() {
	return "Mechanic [dni=" + dni + ", surname=" + surname + ", name="
		+ name + "]";
    }

}
