/**
 * @author Pawel Wloch @SoftLab
 * @date 15.10.2018
 */
package org.softlab.cs.tradevalidator.validation.rules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.softlab.cs.tradevalidator.validation.utils.ResourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import livr.FunctionKeeper;
import livr.LIVRUtils;

@Component(value="currencyRules")
public class CurrencyRules implements RulesProvider {

	@Autowired
	private ResourceProvider resourceProvider;

    private Map<String, Function> rules;

    @PostConstruct
    public void init() {
    	rules = new HashMap<>();
    	rules.put("iso_currency", iso_currency);
    	rules.put("iso_currency_pair", iso_currency_pair);
    }

	/* (non-Javadoc)
	 * @see org.softlab.cs.tradevalidator.validation.rules.RulesProvider#provideRules()
	 */
	@Override
	public Map<String, Function> getProvidedRules() {
		return rules;
	}

    /**
    *	@return error msg when argument or referenced field do not match ISO-4217 currency format
    */
    public Function<List<Object>, Function> iso_currency = objects -> {
        final String field = objects.get(0) + "";
        return (Function<FunctionKeeper, Object>) (wrapper) -> {
            if (LIVRUtils.isNoValue(wrapper.getValue())) return "";
            if (!LIVRUtils.isPrimitiveValue(wrapper.getValue())) return "FORMAT_ERROR";
            
            String value = wrapper.getValue() + "";
            if(resourceProvider.getCurrencyInfoMap().containsKey(value)) {
            	return "";
            }
            return "NOT_RECOGNIZED_AS_ISO-4217_CURRENCY_CODE";
        };
    };

    /**
    *	@return error msg when argument or referenced field do not match ISO-4217 currency format
    */
    public Function<List<Object>, Function> iso_currency_pair = objects -> {
        final String field = objects.get(0) + "";
        return (Function<FunctionKeeper, Object>) (wrapper) -> {
            if (LIVRUtils.isNoValue(wrapper.getValue())) return "";
            if (!LIVRUtils.isPrimitiveValue(wrapper.getValue())) return "FORMAT_ERROR";
            String value = wrapper.getValue() + "";

            if(value.length()!=6) return "FORMAT_ERROR";
            String firstCurr = value.substring(0, 3);
            if(!resourceProvider.getCurrencyInfoMap().containsKey(firstCurr)) {
            	return "PAIR_NOT_RECOGNIZED_AS_ISO-4217_CURRENCY_CODES";
            }
            String secondCurr = value.substring(3, 6);
            if(!resourceProvider.getCurrencyInfoMap().containsKey(secondCurr)) {
            	return "PAIR_NOT_RECOGNIZED_AS_ISO-4217_CURRENCY_CODES";
            }
            return "";
        };
    };

}
