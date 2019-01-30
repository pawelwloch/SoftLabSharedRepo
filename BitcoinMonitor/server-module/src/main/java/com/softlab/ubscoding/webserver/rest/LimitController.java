/**
 * Contains REST methods to set and delete exchange rate values for a given currency pairs.
 *  
 * @author Paweł Włoch ©SoftLab
 * @date 16 sty 2019
 */
package com.softlab.ubscoding.webserver.rest;

import java.math.BigDecimal;

import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softlab.ubscoding.webserver.model.LimitsWrapper;

@RestController
@RequestMapping("/alert")
public class LimitController {

	@Autowired
	private LimitsWrapper limitWrapper;

	@PutMapping
	public void setLimit(@RequestParam(name = "pair", required = true) String strCurrPair,
			@RequestParam(name = "limit", required = true) BigDecimal limitVal) throws Exception {
		CurrencyPair pair = new CurrencyPair(strCurrPair);
		limitWrapper.setLimitForCurrPair(pair, limitVal);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteLimit(@RequestParam(name = "pair", required = true) String strCurrPair) throws Exception {
		CurrencyPair currPair = new CurrencyPair(strCurrPair);
		limitWrapper.deleteLimitForCurrPair(currPair);
	}
}
