package uo.ri.cws.application.service.paymentmean.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Cash;
import uo.ri.cws.domain.PaymentMean;

public class DeletePaymentMean implements Command<Void> {

    private String paymentMeanId;
    private PaymentMeanRepository paymentMeanRepository = Factory.repository
	    .forPaymentMean();

    public DeletePaymentMean(String id) {
	ArgumentChecks.isNotEmpty(id);
	this.paymentMeanId = id;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<PaymentMean> oPaymentMean = paymentMeanRepository
		.findById(paymentMeanId);
	BusinessChecks.exists(oPaymentMean);
	BusinessChecks.isTrue(!(oPaymentMean.get() instanceof Cash));
	BusinessChecks.isTrue(oPaymentMean.get().getCharges().isEmpty());

	paymentMeanRepository.remove(oPaymentMean.get());

	return null;
    }

}
