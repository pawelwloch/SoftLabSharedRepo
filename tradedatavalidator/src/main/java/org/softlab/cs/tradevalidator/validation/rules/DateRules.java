/**
 * @author Pawel Wloch @SoftLab
 * @date 15.10.2018
 */
package org.softlab.cs.tradevalidator.validation.rules;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateParser;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.stereotype.Component;

import livr.FunctionKeeper;
import livr.LIVRUtils;

@Component(value="dateRules")
public class DateRules implements RulesProvider {

	private static final DateParser DATE_PARSER = FastDateFormat.getInstance("yyyy-MM-dd");

    private Map<String, Function> rules;

    @PostConstruct
    public void init() {
    	rules = new HashMap<>();
    	rules.put("equal_or_later_then", equal_or_later_then);
    	rules.put("before", before);
    	rules.put("after", after);
    	rules.put("weekday", weekday);
    }

    /* (non-Javadoc)
	 * @see org.softlab.cs.tradevalidator.validation.rules.RulesProvider#provideRules()
	 */
	@Override
	public Map<String, Function> getProvidedRules() {
		return rules;
	}

	public Function<List<Object>, Function> equal_or_later_then = objects -> {
        final String field = objects.get(0) + "";
        return (Function<FunctionKeeper, Object>) (wrapper) -> {
        	String err = checkArgumentAndReferencedFieldSyntax(wrapper, field);
        	if (err!=null) return err;

        	String value = wrapper.getValue() + "";
        	String dateComparedStr = (String) wrapper.getArgs().get(field);
            try {
				Date date1 = DATE_PARSER.parse(value);
				Date date2 = DATE_PARSER.parse(dateComparedStr);
				
				if(date1.before(date2)) {
					return "BEFORE " + field;
				} else {
					return "";
				}
			} catch (java.text.ParseException e) {
				// should not happen at this point
				e.printStackTrace();
				return "DATES_NOT_CONFORM_TO_ISO_8601";
			}
        };
    };

    public Function<List<Object>, Function> before = objects -> {
        final String field = objects.get(0) + "";
        return (Function<FunctionKeeper, Object>) (wrapper) -> {
        	String err = checkArgumentAndReferencedFieldSyntax(wrapper, field);
        	if (err!=null) return err;

        	String value = wrapper.getValue() + "";
        	String dateComparedStr = (String) wrapper.getArgs().get(field);
            try {
				Date date1 = DATE_PARSER.parse(value);
				Date date2 = DATE_PARSER.parse(dateComparedStr);
				
				if(!date1.before(date2)) {
					return "LATER_OR_EQUAL_TO " + field;
				} else {
					return "";
				}
			} catch (java.text.ParseException e) {
				// should not happen at this point
				e.printStackTrace();
				return "DATES_NOT_CONFORM_TO_ISO_8601";
			}
        };
    };

    public Function<List<Object>, Function> after = objects -> {
        final String field = objects.get(0) + "";
        return (Function<FunctionKeeper, Object>) (wrapper) -> {
        	String err = checkArgumentAndReferencedFieldSyntax(wrapper, field);
        	if (err!=null) return err;

        	String value = wrapper.getValue() + "";
        	String dateComparedStr = (String) wrapper.getArgs().get(field);
            try {
				Date date1 = DATE_PARSER.parse(value);
				Date date2 = DATE_PARSER.parse(dateComparedStr);
				
				if(!date1.after(date2)) {
					return "EARLIER_OR_EQUAL_TO " + field;
				} else {
					return "";
				}
			} catch (java.text.ParseException e) {
				// should not happen at this point
				e.printStackTrace();
				return "DATES_NOT_CONFORM_TO_ISO_8601";
			}
        };
    };

    public Function<List<Object>, Function> weekday = objects -> {
        return (Function<FunctionKeeper, Object>) (wrapper) -> {
            if (LIVRUtils.isNoValue(wrapper.getValue())) return "";
            if (!LIVRUtils.isPrimitiveValue(wrapper.getValue())) return "FORMAT_ERROR";
            String value = wrapper.getValue() + "";

            if (!value.matches("^([0-9]{4})(-?)(1[0-2]|0[1-9])\\2(3[01]|0[1-9]|[12][0-9])$")) {
                return "WRONG_DATE";
            }
            
			int year = Integer.parseInt(value.substring(0, 4));
		    int month = Integer.parseInt(value.substring(5, 7));
		    int day = Integer.parseInt(value.substring(8, 10));
		    Calendar cal = new GregorianCalendar(year, month - 1, day);
		    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		    if(Calendar.SUNDAY == dayOfWeek || Calendar.SATURDAY == dayOfWeek) {
		    	return "WEEKEND";
		    }
				
            return "";
        };
    };

    
    /**
    *  @return error msg when argument or referenced field do not match ISO 8601 date format
    *  otherwise returns null
    */
    private String checkArgumentAndReferencedFieldSyntax(FunctionKeeper wrapper, String field) {
        if (LIVRUtils.isNoValue(wrapper.getValue())) return "";
        if (!LIVRUtils.isPrimitiveValue(wrapper.getValue())) return "FORMAT_ERROR";
        
        String value = wrapper.getValue() + "";

        if (!value.matches("^([0-9]{4})(-?)(1[0-2]|0[1-9])\\2(3[01]|0[1-9]|[12][0-9])$")) {
            return "WRONG_DATE_TO_COMPARE_FORMAT";
        }
        
        String dateComparedStr = (String) wrapper.getArgs().get(field);
        
        if (!dateComparedStr.matches("^([0-9]{4})(-?)(1[0-2]|0[1-9])\\2(3[01]|0[1-9]|[12][0-9])$")) {
            return "WRONG_COMPARED_DATE_FORMAT";
        }
        
        return null;
    }

}
