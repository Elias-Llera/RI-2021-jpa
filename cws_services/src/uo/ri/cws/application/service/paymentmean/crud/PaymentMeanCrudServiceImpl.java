package uo.ri.cws.application.service.paymentmean.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService;
import uo.ri.cws.application.service.paymentmean.crud.command.AddCard;
import uo.ri.cws.application.service.paymentmean.crud.command.AddVoucher;
import uo.ri.cws.application.service.paymentmean.crud.command.DeletePaymentMean;
import uo.ri.cws.application.service.paymentmean.crud.command.FindById;
import uo.ri.cws.application.service.paymentmean.crud.command.FindPaymentMeansByClientId;
import uo.ri.cws.application.util.command.CommandExecutor;

public class PaymentMeanCrudServiceImpl implements PaymentMeanCrudService{

	CommandExecutor executor = Factory.executor.forExecutor();
	
	@Override
	public CardDto addCard(CardDto card) throws BusinessException {
		return executor.execute(new AddCard(card));
	}

	@Override
	public VoucherDto addVoucher(VoucherDto voucher) throws BusinessException {
		return executor.execute(new AddVoucher(voucher));
	}

	@Override
	public void deletePaymentMean(String id) throws BusinessException {
		executor.execute(new DeletePaymentMean(id));		
	}

	@Override
	public Optional<PaymentMeanDto> findById(String id)
			throws BusinessException {
		return executor.execute(new FindById(id));
	}

	@Override
	public List<PaymentMeanDto> findPaymentMeansByClientId(String id)
			throws BusinessException {
		return executor.execute(new FindPaymentMeansByClientId(id));
	}

}
