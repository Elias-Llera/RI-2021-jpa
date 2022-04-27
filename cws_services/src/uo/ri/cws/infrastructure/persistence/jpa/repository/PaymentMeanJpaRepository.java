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
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public List<PaymentMean> findPaymentMeansByInvoiceId(String idFactura) {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public List<PaymentMean> findByClientId(String id) {
		return Jpa.getManager().createNamedQuery("PaymentMean.findByClientId",
				PaymentMean.class).setParameter(1, id).getResultList();
	}

	@Override
	public Object[] findAggregateVoucherDataByClientId(String id) {
		Object[] result = new Object[3];
		result[0] = Jpa.getManager().createNamedQuery("PaymentMean.findNumberOfVouchersByClientId",
				PaymentMean.class).setParameter(1, id).getSingleResult();
		result[1] = Jpa.getManager().createNamedQuery("PaymentMean.findAvailableBalanceOfVouchersByClientId",
				PaymentMean.class).setParameter(1, id).getSingleResult();
		result[2] = Jpa.getManager().createNamedQuery("PaymentMean.findConsumedBalanceOfVouchersByClientId",
				PaymentMean.class).setParameter(1, id).getSingleResult();
		return result;
	}

	@Override
	public Optional<CreditCard> findCreditCardByNumber(String pan) {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public List<Voucher> findVouchersByClientId(String id) {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public Optional<Voucher> findVoucherByCode(String code) {
		throw new RuntimeException("Not yet implemented");
	}

}
