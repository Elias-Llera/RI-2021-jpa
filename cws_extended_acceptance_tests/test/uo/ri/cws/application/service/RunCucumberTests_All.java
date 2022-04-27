package uo.ri.cws.application.service;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
	features = { 
		"test/uo/ri/cws/application/service/client",
		"test/uo/ri/cws/application/service/mechanic",
		"test/uo/ri/cws/application/service/invoice",
		"test/uo/ri/cws/application/service/paymentmean"
	}, 
	plugin = { 
		"pretty",
		"html:target/cucumber-results.html" 
	}, 
	snippets = SnippetType.CAMELCASE
)
public class RunCucumberTests_All {
}
