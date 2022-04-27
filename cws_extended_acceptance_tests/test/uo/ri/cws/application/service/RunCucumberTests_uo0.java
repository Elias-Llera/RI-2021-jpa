package uo.ri.cws.application.service;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
	features = {
		// Basic use cases
		"test/uo/ri/cws/application/service/mechanic",
		"test/uo/ri/cws/application/service/invoice/CreateInvoiceFor.feature",
		
		// Manager: Automatic voucher generation (only number 1)
		"test/uo/ri/cws/application/service/paymentmean/GenerateVouchers25.feature",
		
		// Manager: Voucher list.
		"test/uo/ri/cws/application/service/paymentmean/ListVouchers.feature",
		"test/uo/ri/cws/application/service/paymentmean/ListVouchersSummary.feature",
		
		// Cashier: Methods of payment management
		"test/uo/ri/cws/application/service/paymentmean/AddCreditCard.feature",
		"test/uo/ri/cws/application/service/paymentmean/AddVoucher.feature",
		"test/uo/ri/cws/application/service/paymentmean/DeletePaymentMean.feature",
		"test/uo/ri/cws/application/service/paymentmean/FindPaymentMeanById.feature",
		"test/uo/ri/cws/application/service/paymentmean/FindPaymentMeanByClientId.feature",
	}, 
	plugin = { 
		"pretty",
		"html:target/cucumber-results.html" 
	}, 
	snippets = SnippetType.CAMELCASE
)
public class RunCucumberTests_uo0 {
}
