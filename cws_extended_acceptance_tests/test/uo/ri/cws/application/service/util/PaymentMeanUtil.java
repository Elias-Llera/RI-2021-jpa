package uo.ri.cws.application.service.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CardDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.CashDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.PaymentMeanDto;
import uo.ri.cws.application.service.paymentmean.PaymentMeanCrudService.VoucherDto;
import uo.ri.cws.application.service.util.sql.FindCardByIdSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindCardsByClientIdSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindCashByClientIdSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindCashByIdSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindVoucherByIdSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindVouchersByClientIdSqlUnitOfWork;

public class PaymentMeanUtil {

	public Optional<CashDto> findCashByClientId(String clientid) {
		return new FindCashByClientIdSqlUnitOfWork(clientid).execute().get();
	}

	public List<PaymentMeanDto> findPaymentMeansByClientId(String id) {
		List<PaymentMeanDto> res = new ArrayList<>();
		res.addAll( findVouchersByClientId(id) );
		res.addAll( findCardsByClientId(id) );
		Optional<CashDto> oc = findCashByClientId(id); 
		if ( oc.isPresent() ) {
			res.add( oc.get() );
		}
		return res;
	}

	public List<CardDto> findCardsByClientId(String id) {
		return new FindCardsByClientIdSqlUnitOfWork(id).execute().getAll();
	}

	public List<VoucherDto> findVouchersByClientId(String id) {
		return new FindVouchersByClientIdSqlUnitOfWork(id).execute().getAll();
	}

	public Optional<? extends PaymentMeanDto> findPaymentMeanById(String id) {
		Optional<? extends PaymentMeanDto> res;
		res = new FindCashByIdSqlUnitOfWork(id).execute().get();
		if ( res.isEmpty() ) {
			res = new FindCardByIdSqlUnitOfWork(id).execute().get();
		}
		if ( res.isEmpty() ) {
			res = new FindVoucherByIdSqlUnitOfWork(id).execute().get();
		}
		return res;
	}

}
