/**
 * @author Paweł Włoch ©SoftLab
 * @date 25 sty 2019
 */
package com.softlab.ubscoding.webserver.test.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.softlab.ubscoding.webserver.service.XChangeService;

@RunWith(SpringRunner.class)
@ContextConfiguration
@TestPropertySource("classpath:application-test.properties")
public class XChangeServiceTest {

	@Configuration
	@ComponentScan(basePackages = "com.softlab.ubscoding.webserver.service", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = com.softlab.ubscoding.webserver.service.AlertService.class))
	static class Config {
	}

	@Autowired
	private XChangeService xChangeService;

	@Test
	public void shouldConnectWithBitcoinAndReturnNotNullTicker() throws Exception {
		Ticker ticker = xChangeService.getTickerForCurrencyPair(CurrencyPair.BTC_USD);
		assertNotNull(ticker);
	}

}
