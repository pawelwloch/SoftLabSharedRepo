/**
 * With a constant frequency, using the injected {@XChangeService} checks the exchange rates of all currency pairs
 * stored in a {@link LimitsWrapper} and in case of exceeding the limit publishes alert to the Simple Broker on /topic/limit-alerts.
 * 
 * @author Paweł Włoch ©SoftLab
 * @date 16 sty 2019
 */
package com.softlab.ubscoding.webserver.service;

import java.math.BigDecimal;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.softlab.ubscoding.webserver.model.AlertData;
import com.softlab.ubscoding.webserver.model.LimitsWrapper;

@Service
public class AlertService {

	private static Logger LOG = LoggerFactory.getLogger(AlertService.class);

	@Autowired
	private XChangeService bitcoinService;

	@Autowired
	private LimitsWrapper limits;

	private SimpMessagingTemplate template;

	@Autowired
	public AlertService(SimpMessagingTemplate template) {
		this.template = template;
	}

	@Scheduled(fixedDelay = 1000)
	public void checkLimitAndSendAlert() throws InterruptedException, Exception {
		limits.forEach(entry -> {
			CurrencyPair pair = entry.getKey();
			BigDecimal limit = entry.getValue();
			Ticker ticker;

			try {
				ticker = bitcoinService.getTickerForCurrencyPair(pair);
			} catch (Exception e) {
				LOG.error("BitcoinService thrown exception:" + e.getMessage());
				throw new RuntimeException(e);
			}
			if (ticker.getLast() != null) {
				if (ticker.getLast().compareTo(limit) == 1) {
					publishAlert(pair, ticker);
				}
			}
		});
	}

	private void publishAlert(CurrencyPair pair, Ticker ticker) {
		AlertData alert = new AlertData();
		alert.setCurrencyPair(pair.toString());
		alert.setLimit(ticker.getLast());
		alert.setTimestamp(ticker.getTimestamp());

		this.template.convertAndSend("/topic/limit-alerts", alert);
		LOG.trace("Sent new alert to simplebroker: " + alert.toString());
	}
}
