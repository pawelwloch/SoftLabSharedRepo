/**
 * @author Pawel Wloch ©SoftLab
 * @date 10.11.2018
 */
package org.softlab.codingtask.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softlab.codingtask.model.JsonLogEvent;
import org.softlab.codingtask.model.LogEvent;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class LogEventProcessor implements ItemProcessor<JsonLogEvent, LogEvent> {
	
	private static final Logger log = LoggerFactory.getLogger(LogEventProcessor.class);

	private Validator validator;
	
	//objects are held in the map until matching record is read
	private Map<String, JsonLogEvent> eventsMap = new HashMap<>();
	
	@PostConstruct
	public void init() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public LogEvent process(JsonLogEvent item) throws Exception {
		log.info("Processing log entry (" + item + ")");

		if(!validateJsonObject(item)) return null;
		LogEvent logEvent = createLogEventObj(item);
		return logEvent;
	}

	/**
	 * Validates passed object using explicit call to a Hibernate validator.
	 * Prints validation messages to log. 
	 * @param item
	 * @return true if validation passed or false otherwise.
	 */
	private boolean validateJsonObject(JsonLogEvent item) {
		Set<ConstraintViolation<JsonLogEvent>> constraintViolations = validator.validate(item);
		if(!constraintViolations.isEmpty()) {
			final StringBuilder msg = new StringBuilder();
			constraintViolations.forEach(errMsg -> msg.append(errMsg.getMessage()).append(", "));
			msg.delete(msg.length()-2, msg.length()-1);
			log.error("\tobject won't be processed due to following validation errors: " + msg.toString());
			return false;
		}
		return true;
	}

	private LogEvent createLogEventObj(JsonLogEvent logItem) {
		String id = logItem.getId();
		if(eventsMap.containsKey(id)) {
			JsonLogEvent logItem2 = eventsMap.get(id);
			eventsMap.remove(id);
			long duration = Math.abs(logItem.getTimestamp() - logItem2.getTimestamp());
			boolean alarm = duration > 4l ? true : false;
			LogEvent logEvent = new LogEvent(logItem.getId(), duration, logItem.getType(), logItem.getHost(), alarm);
			log.info("Created a new log event obj: {" + logEvent.toString() + "}");
			return logEvent; 			
		}
		//keep first information about an event with this id in the map
		eventsMap.put(logItem.getId(), logItem);
		return null;
	}

}
