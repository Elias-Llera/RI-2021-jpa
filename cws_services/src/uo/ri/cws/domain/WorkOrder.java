package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.ArgumentChecks;
import alb.util.math.Round;
import uo.ri.cws.domain.Invoice.InvoiceStatus;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TWorkOrders", uniqueConstraints = {
	@UniqueConstraint(columnNames = { "VEHICLE_ID", "DATE" }) })
public class WorkOrder extends BaseEntity {

    public enum WorkOrderStatus {
	OPEN, ASSIGNED, FINISHED, INVOICED
    }

    // natural attributes
    private LocalDateTime date;
    private String description;
    private double amount = 0.0;
    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status = WorkOrderStatus.OPEN;
    private boolean usedForVoucher;

    // accidental attributes
    @ManyToOne
    private Vehicle vehicle;
    @ManyToOne
    private Mechanic mechanic;
    @ManyToOne
    private Invoice invoice;
    @OneToMany(mappedBy = "workOrder")
    private Set<Intervention> interventions = new HashSet<>();

    WorkOrder() {
    }

    public WorkOrder(Vehicle vehicle) {
	ArgumentChecks.isNotNull(vehicle);

	this.date = LocalDateTime.now();

	Associations.Fix.link(vehicle, this);

	this.description = "no-description";
    }

    public WorkOrder(Vehicle vehicle, String description) {
	this(vehicle);

	ArgumentChecks.isNotEmpty(description);
	this.description = description;
    }

    public WorkOrder(Vehicle vehicle, LocalDateTime date, String description) {
	this(vehicle, description);
	ArgumentChecks.isNotNull(date);
	this.date = date;
    }

    /**
     * Changes it to INVOICED state given the right conditions This method is
     * called from Invoice.addWorkOrder(...)
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not FINISHED, or -
     *                               The work order is not linked with the
     *                               invoice
     */
    public void markAsInvoiced() {
	if (!status.equals(WorkOrderStatus.FINISHED)) {
	    throw new IllegalStateException(
		    "The state of the work order must be FINISHED.");
	}
	if (invoice == null) {
	    throw new IllegalStateException(
		    "The work order is not linked with any invoice.");
	}
	status = WorkOrderStatus.INVOICED;
    }

    /**
     * Changes it to FINISHED state given the right conditions and computes the
     * amount
     *
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in ASSIGNED
     *                               state, or - The work order is not linked
     *                               with a mechanic
     */
    public void markAsFinished() {
	if (!status.equals(WorkOrderStatus.ASSIGNED)) {
	    throw new IllegalStateException(
		    "The state of the work order must be ASSIGNED.");
	}
	if (mechanic == null) {
	    throw new IllegalStateException(
		    "The work order is not linked with any mechanic.");
	}
	computeAmount();
	status = WorkOrderStatus.FINISHED;
    }

    private void computeAmount() {
	amount = 0;
	for (Intervention intervention : interventions) {
	    amount += intervention.getAmount();
	}
	amount = Round.twoCents(amount);
    }

    /**
     * Changes it back to FINISHED state given the right conditions This method
     * is called from Invoice.removeWorkOrder(...)
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not INVOICED, or -
     *                               The work order is still linked with the
     *                               invoice
     */
    public void markBackToFinished() {
	if (!status.equals(WorkOrderStatus.INVOICED)) {
	    throw new IllegalStateException(
		    "The state of the work order must be INVOICED.");
	}
	if (invoice != null) {
	    throw new IllegalStateException(
		    "The work order is still linked with an invoice.");
	}
	status = WorkOrderStatus.FINISHED;
    }

    /**
     * Links (assigns) the work order to a mechanic and then changes its status
     * to ASSIGNED
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in OPEN status,
     *                               or - The work order is already linked with
     *                               another mechanic
     */
    public void assignTo(Mechanic mechanic) {
	if (!status.equals(WorkOrderStatus.OPEN)) {
	    throw new IllegalStateException(
		    "The state of the work order must be OPEN.");
	}
	if (this.mechanic != null) {
	    throw new IllegalStateException(
		    "The work order is  linked with another mechanic.");
	}
	Associations.Assign.link(mechanic, this);
	status = WorkOrderStatus.ASSIGNED;
    }

    /**
     * Unlinks (deassigns) the work order and the mechanic and then changes its
     * status back to OPEN
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in ASSIGNED
     *                               status
     */
    public void desassign() {
	if (!status.equals(WorkOrderStatus.ASSIGNED)) {
	    throw new IllegalStateException(
		    "The state of the work order must be ASSIGNED.");
	}
	Associations.Assign.unlink(mechanic, this);
	status = WorkOrderStatus.OPEN;
    }

    /**
     * In order to assign a work order to another mechanic is first have to be
     * moved back to OPEN state and unlinked from the previous mechanic.
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in FINISHED
     *                               status
     */
    public void reopen() {
	if (!status.equals(WorkOrderStatus.FINISHED)) {
	    throw new IllegalStateException(
		    "The state of the work order must be FINISHED.");
	}
	status = WorkOrderStatus.OPEN;
	Associations.Assign.unlink(mechanic, this);
    }

    public Set<Intervention> getInterventions() {
	return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
	return interventions;
    }

    void _setVehicle(Vehicle vehicle) {
	this.vehicle = vehicle;
    }

    void _setMechanic(Mechanic mechanic) {
	this.mechanic = mechanic;
    }

    void _setInvoice(Invoice invoice) {
	this.invoice = invoice;
    }

    public LocalDateTime getDate() {
	return date;
    }

    public String getDescription() {
	return description;
    }

    public double getAmount() {
	return amount;
    }

    public WorkOrderStatus getStatus() {
	return status;
    }

    public Vehicle getVehicle() {
	return vehicle;
    }

    public Mechanic getMechanic() {
	return mechanic;
    }

    public Invoice getInvoice() {
	return invoice;
    }

    public boolean isInvoiced() {
	return status.equals(WorkOrderStatus.INVOICED);
    }

    public boolean isInvoicedAndPaid() {
	return isInvoiced() && invoice.getStatus().equals(InvoiceStatus.PAID);
    }

    public boolean isFinished() {
	return status.equals(WorkOrderStatus.FINISHED);
    }

    public boolean canBeUsedForVoucher() {
	return !usedForVoucher && isInvoicedAndPaid();
    }

    public void markAsUsedForVoucher() {
	this.usedForVoucher = true;
    }

    @Override
    public String toString() {
	return "WorkOrder [date=" + date + ", description=" + description
		+ ", amount=" + amount + ", status=" + status + ", vehicle="
		+ vehicle + "]";
    }

}
