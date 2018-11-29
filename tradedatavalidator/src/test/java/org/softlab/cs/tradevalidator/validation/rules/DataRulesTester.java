/**
 * @author Pawel Wloch @SoftLab
 * @date 15.10.2018
 */
package org.softlab.cs.tradevalidator.validation.rules;

import static org.junit.Assert.assertEquals;
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
public class DataRulesTester {

	@Autowired
	@Qualifier("dateRules")
	private RulesProvider dateRulesProvider;

	private Validator validator;
	private JSONParser parser = new JSONParser();
	
	static private String valueDateWeekdayRule = "{\"valueDate\": [\"iso_date\",\"weekday\"]}";
	static private String valueDateShouldBeEqualOrLaterThenTradeDateRule = "{\"valueDate\": [\"iso_date\", {\"equal_or_later_then\":\"tradeDate\"}]}";
	
	private Validator initializeAndSetValidator(String initRule) throws ParseException {
		validator = LIVR.validator().init(initRule, false);
		return validator.registerRules(dateRulesProvider.getProvidedRules());
	}

	@Test
	public void shouldValidateDayOfWeekendOk() throws IOException, ParseException {
		String valueDateWeekdayJson = "{\"valueDate\":\"2018-10-10\"}";
		JSONObject jsonObj = (JSONObject) parser.parse(valueDateWeekdayJson);
		JSONObject validData = initializeAndSetValidator(valueDateWeekdayRule).validate(jsonObj);
		// the tested date is a weekday so validData should not be null
		// (validation passed)
		assertNotNull(validData);
		assertNull(validator.getErrors());
	}

	@Test
	public void shouldHaveErrorOnWeekendDay() throws IOException, ParseException {
		String valueDateWeekendJson = "{\"valueDate\":\"2018-10-20\"}";
		JSONObject jsonObj = (JSONObject) parser.parse(valueDateWeekendJson);
		JSONObject validData = initializeAndSetValidator(valueDateWeekdayRule).validate(jsonObj);
		// the tested date is a weekend day so validData should be null
		// (validation passed)
		assertNull(validData);
		assertEquals(validator.getErrors().toJSONString(), "{\"valueDate\":\"WEEKEND\"}");
	}

	@Test
	public void shoudHaveErrorOnValueDateBeforeTradeDate() throws IOException, ParseException {
		String valueDateBeforeTradeDate = "{\"valueDate\":\"2018-10-20\",\"tradeDate\":\"2018-10-21\"}";
		JSONObject jsonObj = (JSONObject) parser.parse(valueDateBeforeTradeDate);
		JSONObject validData = initializeAndSetValidator(valueDateShouldBeEqualOrLaterThenTradeDateRule).validate(jsonObj);
		assertNull(validData);
		assertEquals("{\"valueDate\":\"BEFORE tradeDate\"}", validator.getErrors().toJSONString());
	}

	@Test
	public void shoudNotHaveErrorOnValueDateEqualTradeDate() throws IOException, ParseException {
		String valueDateBeforeTradeDate = "{\"valueDate\":\"2018-10-20\",\"tradeDate\":\"2018-10-20\"}";
		JSONObject jsonObj = (JSONObject) parser.parse(valueDateBeforeTradeDate);
		JSONObject validData = initializeAndSetValidator(valueDateShouldBeEqualOrLaterThenTradeDateRule).validate(jsonObj);
		assertNotNull(validData);
		assertNull(validator.getErrors());
	}

}
