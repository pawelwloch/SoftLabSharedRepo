/**
 * @author Paweł Włoch ©SoftLab
 * @date 25 sty 2019
 */
package com.softlab.ubscoding.webserver.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

public interface XChangeService {

	public Ticker getTickerForCurrencyPair(CurrencyPair pair) throws Exception;
}
