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
		
		// Manager: Automatic voucher generation (only number 2 and 3)
		"test/uo/ri/cws/application/service/paymentmean/GenerateVouchers20.feature",
		"test/uo/ri/cws/application/service/paymentmean/GenerateVouchers30.feature",
		
		// Manager: Voucher list.
		"test/uo/ri/cws/application/service/paymentmean/ListVouchers.feature",
		"test/uo/ri/cws/application/service/paymentmean/ListVouchersSummary.feature",

		// Cashier. Settle invoice.
		"test/uo/ri/cws/application/service/invoice/FindNotInvoicedWorkOrdersByClientDni.feature",
		"test/uo/ri/cws/application/service/invoice/SettleInvoice.feature",
	}, 
	plugin = { 
		"pretty",
		"html:target/cucumber-results.html" 
	}, 
	snippets = SnippetType.CAMELCASE
)
public class RunCucumberTests_uo2 {
}
