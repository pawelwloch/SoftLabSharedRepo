/**
 * @author Pawel Wloch @SoftLab
 * @date 14.10.2018
 */
package org.softlab.cs.tradevalidator.validation.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateParser;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softlab.cs.tradevalidator.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class ResourceProviderTests {

	private static final DateParser DATE_PARSER = FastDateFormat.getInstance("yyyy-MM-dd");

	@Autowired
	private ResourceProvider provider;
	
	@Test
	public void shoudReturnNotNullLIVRValidationRules() {
		String livrRules = provider.getLIVRRules();
		assertNotNull(livrRules);
	}
	
	@Test
	public void shoudInitializeCurrencyData() throws ParseException  {
		Map<String, List<Date>> currencyData = provider.getCurrencyInfoMap();
		assertNotNull(currencyData);
		assertEquals("usd symbol not in currency map", true, currencyData.containsKey("USD"));
		Date date1 = DATE_PARSER.parse("2018-01-01");
		assertEquals(0,date1.compareTo(currencyData.get("USD").get(0)));
		Date date2 = DATE_PARSER.parse("2018-01-15");
		assertEquals(0,date2.compareTo(currencyData.get("USD").get(1)));
	}

}
