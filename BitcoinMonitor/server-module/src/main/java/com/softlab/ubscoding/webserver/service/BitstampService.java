/**
 * @author Paweł Włoch ©SoftLab
 * @date 25 sty 2019
 */
package com.softlab.ubscoding.webserver.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Service
public class BitstampService implements XChangeService {

	private static Logger LOG = LoggerFactory.getLogger(BitstampService.class);

	private Exchange bitStamp;
	private MarketDataService marketDataService;

	@PostConstruct
	public void init() {
		bitStamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());
		marketDataService = bitStamp.getMarketDataService();
	}

	@Override
	public Ticker getTickerForCurrencyPair(CurrencyPair currPair) throws IOException {
		LOG.debug("called with a curr pair:" + currPair);
		Ticker ticker = marketDataService.getTicker(currPair);
		LOG.debug("received a new exchange rate data:" + ticker.toString());
		return ticker;
	}

}
