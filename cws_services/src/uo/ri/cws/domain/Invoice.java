package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import alb.util.math.Round;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TInvoices")
public class Invoice extends BaseEntity {

    public enum InvoiceStatus {
	NOT_YET_PAID, PAID
    }

    // natural attributes
    @Column(unique = true)
    private Long number;
    private LocalDate date;
    private double amount;
    private double vat;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status = InvoiceStatus.NOT_YET_PAID;

    private boolean usedForVoucher = false;

    // accidental attributes
    @OneToMany(mappedBy = "invoice")
    private Set<WorkOrder> workOrders = new HashSet<>();
    @OneToMany(mappedBy = "invoice")
    private Set<Charge> charges = new HashSet<>();

    Invoice() {
    }

    public Invoice(Long number) {
	// call full constructor with sensible defaults
	this(number, LocalDate.now(), new ArrayList<>());
    }

    public Invoice(Long number, LocalDate date) {
	// call full constructor with sensible defaults
	this(number, date, new ArrayList<>());
    }

    public Invoice(Long number, List<WorkOrder> workOrders) {
	this(number, LocalDate.now(), workOrders);
    }

    // full constructor
    public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
	// check arguments (always), through IllegalArgumentException
	// store the number
	// store a copy of the date
	// add every work order calling addWorkOrder( w )
	ArgumentChecks.isNotNull(number);
	ArgumentChecks.isNotNull(date);
	ArgumentChecks.isNotNull(workOrders);

	this.number = number;
	this.date = date;
	for (WorkOrder workOrder : workOrders) {
	    addWorkOrder(workOrder);
	}
    }

    /**
     * Computes amount and vat (vat depends on the date)
     */
    private void computeAmount() {
	amount = 0.0;
	for (WorkOrder workOrder : workOrders) {
	    amount += workOrder.getAmount();
	}
	vat = LocalDate.parse("2012-07-01").isBefore(date) ? 21.0 : 18.0;
	amount = amount * (1 + vat / 100);
	amount = Round.twoCents(amount);
    }

    /**
     * 
     * @return double with the total cost of the invoice (including taxes)
     *         rounded to two cents
     */
    public double computeTotal() {
	return amount;
    }

    /**
     * Adds (double links) the workOrder to the invoice and updates the amount
     * and vat
     * 
     * @param workOrder
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
     */
    public void addWorkOrder(WorkOrder workOrder) {
	if (!status.equals(InvoiceStatus.NOT_YET_PAID)) {
	    throw new IllegalStateException(
		    "The invoice state needs to be NOT_YET_PAID.");
	}
	Associations.ToInvoice.link(this, workOrder);
	workOrder.markAsInvoiced();
	computeAmount();
    }

    /**
     * Removes a work order from the invoice and recomputes amount and vat
     * 
     * @param workOrder
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
     */
    public void removeWorkOrder(WorkOrder workOrder) {
	if (!status.equals(InvoiceStatus.NOT_YET_PAID)) {
	    throw new IllegalStateException(
		    "The invoice state needs to be NOT_YET_PAID.");
	}
	Associations.ToInvoice.unlink(this, workOrder);
	workOrder.markBackToFinished();
	computeAmount();
    }

    /**
     * Marks the invoice as PAID, but
     * 
     * @throws IllegalStateException if - Is already settled - Or the amounts
     *                               paid with charges to payment means do not
     *                               cover the total of the invoice
     */
    public void settle() {
	if (status.equals(InvoiceStatus.PAID)) {
	    throw new IllegalStateException("The invoice is already sttled.");
	}

	double margin = 0.01;

	double totalPaid = 0;
	for (Charge charge : charges) {
	    totalPaid += charge.getAmount();
	}

	double totalCharged = amount;

	if (Math.abs(totalCharged - totalPaid) > margin) {
	    throw new IllegalStateException(
		    "The charges don't cover the cost.");
	}

	status = InvoiceStatus.PAID;
    }

    public Set<WorkOrder> getWorkOrders() {
	return new HashSet<>(workOrders);
    }

    Set<WorkOrder> _getWorkOrders() {
	return workOrders;
    }

    public Set<Charge> getCharges() {
	return new HashSet<>(charges);
    }

    Set<Charge> _getCharges() {
	return charges;
    }

    public Long getNumber() {
	return number;
    }

    public LocalDate getDate() {
	return date;
    }

    public double getAmount() {
	return computeTotal();
    }

    public double getVat() {
	return vat;
    }

    public InvoiceStatus getStatus() {
	return status;
    }

    public boolean isNotSettled() {
	return !status.equals(InvoiceStatus.PAID);
    }

    public boolean isSettled() {
	return status.equals(InvoiceStatus.PAID);
    }

    public boolean canGenerate500Voucher() {
	return getAmount() > 500 && !usedForVoucher
		&& status.equals(InvoiceStatus.PAID);
    }

    public void markAsUsed() {
	if (!canGenerate500Voucher()) {
	    throw new IllegalStateException(
		    "Invoice cannot generate a voucher.");
	}
	this.usedForVoucher = true;
    }

    public boolean isUsedForVoucher() {
	return usedForVoucher;
    }

    @Override
    public String toString() {
	return "Invoice [number=" + number + ", date=" + date + ", amount="
		+ amount + ", vat=" + vat + ", status=" + status + "]";
    }

}
