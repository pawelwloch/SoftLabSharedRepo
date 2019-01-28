/**
 * @author Paweł Włoch ©SoftLab
 * @date 27 sty 2019
 */
package com.softlab.ubscoding.cli.rest;

import java.math.BigDecimal;

public interface AlertServiceClient {

	/**
	 * Sets alert to happen when price goes above a limit by calling remote REST put, e.g.: HTTP
	 * PUT/alert?pair=BTC/USD&limit=500
	 * 
	 * @param currencyPair
	 * @param limit
	 */
	public void putAlert(String currencyPair, BigDecimal limit);

	/**
	 * Removes alert by calling remote REST delete, e.g.: HTTP DELETE /alert?pair=BTC-USD&limit=500
	 * 
	 * @param currencyPair
	 */
	public void deleteAlert(String currencyPair);

}
