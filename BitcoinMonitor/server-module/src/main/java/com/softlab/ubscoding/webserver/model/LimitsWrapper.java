/**
 * Declares all methods necessary to  add, remove, check limits
 *  
 * @author Paweł Włoch ©SoftLab
 * @date 26 sty 2019
 */
package com.softlab.ubscoding.webserver.model;

import java.math.BigDecimal;
import java.util.Map.Entry;

import org.knowm.xchange.currency.CurrencyPair;

public interface LimitsWrapper extends Iterable<Entry<CurrencyPair, BigDecimal>> {

	public void setLimitForCurrPair(CurrencyPair pair, BigDecimal limit) throws Exception;

	public void deleteLimitForCurrPair(CurrencyPair pair) throws Exception;

}
