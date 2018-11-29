/**
 * @author Pawel Wloch @SoftLab
 * @date 14.10.2018
 */
package org.softlab.cs.tradevalidator.validation.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ResourceProvider {
	public String getLIVRRules();
	/**
	 * 
	 * @return a map of ISO 4217 currency codes with currency available holidays information 
	 */
	public Map<String, List<Date>> getCurrencyInfoMap();
}
