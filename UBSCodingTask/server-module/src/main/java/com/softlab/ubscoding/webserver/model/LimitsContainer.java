/**
 * Keeps current limits to be checked
 * 
 * @author Paweł Włoch ©SoftLab
 * @date 24 sty 2019
 */
package com.softlab.ubscoding.webserver.model;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.stereotype.Component;

@Component
public class LimitsContainer implements LimitsWrapper {

	private ConcurrentHashMap<CurrencyPair, BigDecimal> limits = new ConcurrentHashMap<CurrencyPair, BigDecimal>();

	@Override
	public void setLimitForCurrPair(CurrencyPair pair, BigDecimal limit) throws Exception {
		// if there is a higher limit for a given pair, overwrite it
		BigDecimal existing = limits.get(pair);
		if (existing == null || existing.compareTo(limit) > 1) {
			limits.put(pair, limit);
		}
	}

	@Override
	public void deleteLimitForCurrPair(CurrencyPair pair) throws Exception {
		limits.remove(pair);
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Entry<CurrencyPair, BigDecimal>> iterator() {
		return limits.entrySet().iterator();
	}
}
