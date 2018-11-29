/**
 * @author Pawel Wloch Â©SoftLab
 * @date 17.10.2018
 */
package org.softlab.cs.tradevalidator.validation.rules;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softlab.cs.tradevalidator.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import livr.LIVR;
import livr.Validator;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class CurrencyRulesTester {

	@Autowired
	@Qualifier("currencyRules")
	private RulesProvider currencyRulesProvider;

	private Validator validator;
	private JSONParser parser = new JSONParser();
	
	static private String ISO_CURRENCY_RULE = "{\"premiumCcy\": [\"iso_currency\"]}";
	
	private Validator initializeAndSetValidator(String initRule) throws ParseException {
		validator = LIVR.validator().init(initRule, false);
		return validator.registerRules(currencyRulesProvider.getProvidedRules());
	}
	
	@Test
	public void shouldValidateUSD() throws IOException, ParseException {
		String usdCurrencyJson = "{\"premiumCcy\":\"USD\"}";
		JSONObject jsonObj = (JSONObject) parser.parse(usdCurrencyJson);
		JSONObject validData = initializeAndSetValidator(ISO_CURRENCY_RULE).validate(jsonObj);
		// the tested currency is valid so validData should not be null (validation passed)
		assertNotNull(validData);
		assertNull(validator.getErrors());
	}



}
