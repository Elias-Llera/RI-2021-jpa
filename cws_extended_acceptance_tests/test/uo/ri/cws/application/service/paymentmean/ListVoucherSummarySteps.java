package uo.ri.cws.application.service.paymentmean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.common.TestContext;
import uo.ri.cws.application.service.paymentmean.VoucherService.VoucherSummaryDto;

public class ListVoucherSummarySteps {

	private VoucherService service = Factory.service.forVoucherService();
	private List<VoucherSummaryDto> theSummary = new ArrayList<>();

	public ListVoucherSummarySteps(TestContext ctx) {

	}

	@When("I list voucher summary")
	public void iListVoucherSummary() throws BusinessException {
		theSummary = service.getVoucherSummary();
	}

	@Then("I get no result")
	public void iGetNoResult() {
		assertTrue( theSummary.isEmpty() );
	}

	@Then("I get the following result")
	public void iGetTheFollowingResult(DataTable data) {
		List<Map<String, String>> table = data.asMaps();
		
		for (Map<String, String> row : table) {
			String dni = row.get("dni");
			List<VoucherSummaryDto> resultsByClient = theSummary.stream()
					.filter(s -> s.dni.equals(dni))
					.collect(Collectors.toList());
			
			assertEquals(1, resultsByClient.size());
			match(resultsByClient.get(0), row);
		}

	}

	private boolean match(VoucherSummaryDto arg, Map<String, String> expected) {
		// | dni | name | surname | issued | accumulated | balance | total |
		assertEquals(expected.get("dni"), arg.dni);
		assertEquals(expected.get("name"), arg.name);
		assertEquals(expected.get("surname"), arg.surname);
		assertEquals(toLong(expected.get("issued")), arg.issued );
		assertEquals(toDouble(expected.get("accumulated")), arg.consumed, 0.01);
		assertEquals(toDouble(expected.get("balance")), arg.availableBalance, 0.01 );
		assertEquals(toDouble(expected.get("total")), arg.totalAmount, 0.01);

		return false;
	}

	private double toDouble(String value) {
		return Double.parseDouble( value );
	}

	private long toLong(String value) {
		return Long.parseLong( value );
	}

}
