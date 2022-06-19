package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.domain.CreditCard;
import uo.ri.cws.domain.PaymentMean;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class PaymentMeanJpaRepository extends BaseJpaRepository<PaymentMean>
	implements PaymentMeanRepository {

    @Override
    public List<PaymentMean> findPaymentMeansByClientId(String id) {
	return Jpa.getManager()
		.createNamedQuery("PaymentMean.findByClientId",
		PaymentMean.class)
		.setParameter(1, id)
		.getResultList();
    }

    @Override
    public List<PaymentMean> findPaymentMeansByInvoiceId(String idFactura) {
	return Jpa.getManager()
		.createNamedQuery("PaymentMean.findByInvoiceId",
			PaymentMean.class)
		.setParameter(1, idFactura)
		.getResultList();
    }

    @Override
    public List<PaymentMean> findByClientId(String id) {
	return Jpa.getManager()
		.createNamedQuery("PaymentMean.findByClientId",
		PaymentMean.class)
		.setParameter(1, id)
		.getResultList();
    }

    @Override
    public Object[] findAggregateVoucherDataByClientId(String id) {
	return Jpa.getManager()
		.createNamedQuery(
			"PaymentMean.findAggregateVoucherDataByClientId",
			Object[].class)
		.setParameter(1, id)
		.getSingleResult();
    }

    @Override
    public Optional<CreditCard> findCreditCardByNumber(String number) {
	return Jpa.getManager()
		.createNamedQuery("PaymentMean.findCreditCardByNumber",
			CreditCard.class)
		.setParameter(1, number)
		.getResultStream()
		.findFirst();
    }

    @Override
    public List<Voucher> findVouchersByClientId(String id) {
	return Jpa.getManager()
		.createNamedQuery("PaymentMean.findVoucherByClientId",
			Voucher.class)
		.setParameter(1, id)
		.getResultList();
    }

    @Override
    public Optional<Voucher> findVoucherByCode(String code) {
	return Jpa.getManager()
		.createNamedQuery("PaymentMean.findVoucherByCode",
			Voucher.class)
		.setParameter(1, code)
		.getResultStream()
		.findFirst();
    }

}
