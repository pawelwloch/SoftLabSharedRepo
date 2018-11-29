/**
 * @author Pawel Wloch ©SoftLab
 * @date 16.10.2018
 */
package org.softlab.cs.tradevalidator.validation.rules;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyData {

	@JsonProperty("cc")
	private String code;
	private List<Date> holidays;

	public String getCode() {
		return code;
	}
	
	public void setCode(String currencyCode) {
		this.code = currencyCode;
	}
	
	public List<Date> getHolidays() {
		return holidays;
	}
	
	public void setHolidays(List<Date> currencyHolidays) {
		this.holidays = currencyHolidays;
	}
}
