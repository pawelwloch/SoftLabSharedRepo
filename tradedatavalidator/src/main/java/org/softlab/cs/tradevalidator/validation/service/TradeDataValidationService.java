/**
 * @author Pawel Wloch @SoftLab
 * @date 12.10.2018
 */
package org.softlab.cs.tradevalidator.validation.service;

import java.io.IOException;

public interface TradeDataValidationService {
	/**
	 * 
	 * @param tradeData to be validated. Should come as json in String
	 * @return json array of json objects written in simple string
	 * @throws IOException (should never)
	 */
	public String validateTradeData(String tradeData);
}
