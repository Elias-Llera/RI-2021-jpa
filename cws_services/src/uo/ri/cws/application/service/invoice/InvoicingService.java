package uo.ri.cws.application.service.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;

/**
 * This service is intended to be used by the Cashier It follows the ISP
 * principle (@see SOLID principles from RC Martin)
 */
public interface InvoicingService {

    /**
     * @formatter:off
     * Create a new invoice billing the work orders in the argument.
     * The new invoice will be in NOT_YET_PAID status, the work orders will be 
     * marked as INVOICED
     * 
     * @param the list of work order id to bill
     * @throws BusinessException if 
     *	- the status of any of the work orders is not FINISHED 
     *	- any of the work orders does not exist
     * @throws IllegalArgumentException if 
     *	- list is null, empty or 
     *	- any of the strings in the list is empty or null
     * @formatter:on
     */
    InvoiceDto createInvoiceFor(List<String> workOrderIds)
	    throws BusinessException;

    /** 
	 * @formatter:off
	 * Returns a list with info of all the work orders of all the client's
	 * vehicles
	 * 
	 * @param dni of the client
	 * @return a list of InvoicingWorkOrderDto or empty list if there is none
	 * @throws BusinessException DOES NOT
     * @formatter:on
	 */
    List<InvoicingWorkOrderDto> findWorkOrdersByClientDni(String dni)
	    throws BusinessException;

    /** 
	 * @formatter:off
	 * Find FINISHED BUT NOT INVOICED work orders due to a client with certain
	 * dni.
	 * 
	 * @param the dni
	 * @throws BusinessException if client with this dni does not exist
	 * @throws IllegalArgumentException if dni is empty
     * @formatter:on
	 */
    List<InvoicingWorkOrderDto> findNotInvoicedWorkOrdersByClientDni(String dni)
	    throws BusinessException;

    /** 
	 * @formatter:off
	 * Returns a list with info of all the work orders of a vehicle
	 * 
	 * @param plate, the plate number of the vehicle
	 * @return a list of InvoicingWorkOrderDto or empty list if there is none
	 * @throws BusinessException DOES NOT
     * @formatter:on
	 */
    List<InvoicingWorkOrderDto> findWorkOrdersByPlateNumber(String plate)
	    throws BusinessException;

    /** 
	 * @formatter:off
	 * @param number, of the invoice
	 * @return the InvoiceDto with the data of the invoice
	 * @throws BusinessException DOES NOT
     * @formatter:on
	 */
    Optional<InvoiceDto> findInvoiceByNumber(Long number)
	    throws BusinessException;

    /** 
	 * @formatter:off
	 * @param dni of the client
	 * @return the list of the PaymentMeanDto of a client represented by the dni
	 *         or an empty list if none
	 * @throws BusinessException DOES NOT
     * @formatter:on
	 */
    List<InvoicingPaymentMeanDto> findPayMeansByClientDni(String dni)
	    throws BusinessException;

    /** 
	 * @formatter:off
     * Creates the charges against the indicated payment means (with the amount
     * indicated) and then pass the invoice to the PAID status.
     *
     * @param invoiceId, the id of the invoice to be paid
     * @param charges, a List of ChargeDto whose: 
     * 	- Key (String) represents the payment mean id, and 
     *	- Value (Double) represents the amount to be charged to the payment mean
     *  
     * @throws IllegalArgumentException if 
     * 	- invoiceId is null or empty 
     * 	- charges is null
     * 
     * @throws BusinessException if 
     * 	- there is no invoice for the invoiceId provided 
     * 	- the indicated invoice is already in PAID status 
     * 	- does not exist any of the payment means indicated by the id 
     * 	- the addition of all amounts charged to each payment mean does not 
     * 		equals the amount of the invoice with a precision of two cents 
     * 		( Math.abs( total - amount) <= 0.01 ) 
     * 	- any of the payment means cannot be used to pay the specified amount: 
     * 		- For a CreditCard, if the current date is after the validThough date 
     * 		- For a Voucher, if it has not enough available for the amount 
     * 		- For Cash there is no constraint, cash can be used always
     *
     * 		Note (JUST FOR JPA IMPLEMENTATION): the domain model we have 
     * 			implemented at lab session does not have the proper design to do
     * 			this polymorphically, THUS
     *				- add a public abstract boolean canPay( amount ); method 
     *					to PaymentMean class, and
     *				- then override the method on the child classes
     * @formatter:on
     */
    void settleInvoice(String invoiceId, Map<String, Double> charges)
	    throws BusinessException;

    public static class InvoiceDto {
	public String id; // the surrogate id (UUID)
	public Long version;

	public double total; // total amount (money) vat included
	public double vat; // amount of vat (money)
	public long number; // the invoice identity, a sequential number
	public LocalDate date; // of the invoice
	public String status = "NOT_YET_PAID"; // the status: PAID, NOT_YET_PAID
	public boolean usedForVoucher;
    }

    public static class ChargeDto {
	public String id;
	public Long version;

	public String invoice_id;
	public String paymentMean_id;
	public double amount;
    }

    // A work order DTO just for the invoicing use case
    public static class InvoicingWorkOrderDto {
	public String id;
	public Long version;

	public String description;
	public LocalDateTime date;
	public String status;
	public double total;
    }

    // A payment mean DTO just for the invoicing use case
    public static abstract class InvoicingPaymentMeanDto {
	public String id;
	public Long version;

	public String clientId;
	public Double accumulated;
    }

}
