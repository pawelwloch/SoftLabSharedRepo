/**
 * @author Pawel Wloch @SoftLab
 * @date 15.10.2018
 */
package org.softlab.cs.tradevalidator.validation.rules;

import java.util.Map;
import java.util.function.Function;

public interface RulesProvider {
	
	/**
	 * @return map of functions implementing rules in this logical module
	 */
	public Map<String, Function> getProvidedRules();
}
