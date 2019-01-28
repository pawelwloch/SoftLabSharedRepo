/**
 * @author Paweł Włoch ©SoftLab
 * @date 25 sty 2019
 */
package com.softlab.ubscoding.webserver.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CurrencyPairTest {

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionReturnEmptyString() {
		String strPair = "USD+PLN";
		CurrencyPair currPair = new CurrencyPair(strPair);
		// to make sure it won't be optimized by compilator
		assertNull(currPair);
	}

	@Test
	public void shouldNotThrowException() {
		String strPair = "BTC/PLN";
		CurrencyPair currPair = new CurrencyPair(strPair);
		assertNotNull(currPair);
	}

}
