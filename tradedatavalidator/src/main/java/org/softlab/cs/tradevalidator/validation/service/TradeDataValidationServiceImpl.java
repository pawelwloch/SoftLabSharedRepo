/**
 * @author Pawel Wloch @SoftLab
 * @date 12.10.2018
 */

package org.softlab.cs.tradevalidator.validation.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.softlab.cs.tradevalidator.validation.rules.RulesProvider;
import org.softlab.cs.tradevalidator.validation.utils.ResourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import livr.LIVR;
import livr.Validator;

@Service
public class TradeDataValidationServiceImpl implements TradeDataValidationService {

	@Autowired
	private ResourceProvider resourceProvider;

	@Autowired
	@Qualifier("dateRules")
	private RulesProvider dateRules;
	
	@Autowired
	@Qualifier("currencyRules")
	private RulesProvider currencyRules;
	
	private Validator validator;
	private String inputError = null;
	
	@PostConstruct
	public void init() throws ParseException, IOException {
		validator = LIVR.validator().init(resourceProvider.getLIVRRules(), false);
		validator.registerRules(dateRules.getProvidedRules());
		validator.registerRules(currencyRules.getProvidedRules());
	}

	private JSONArray parseInputString(String encodedJson) {
		JSONObject parsedObj=null;
		JSONArray parsedArr=null;
		JSONObject error= new JSONObject();
		JSONParser jsonParser = new JSONParser();
		try {
			parsedObj = (JSONObject) jsonParser.parse(encodedJson);
		} catch(ClassCastException castEx) {
			try {
				parsedArr = (JSONArray) jsonParser.parse(encodedJson);
			} catch (Exception e) {
				System.out.println(e.getMessage());
	            error.put("base", "FORMAT_ERROR");
	            inputError = error.toJSONString();
	            return null;
			}
		} catch(Exception otherException) {
			System.out.println(otherException.getMessage());
            error.put("base", "FORMAT_ERROR");
            inputError = error.toJSONString();
			return null;
		}

		if(parsedArr==null) {
			parsedArr = new JSONArray();
			parsedArr.add(parsedObj);
		}

		return parsedArr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String validateTradeData(String passedData) {
		JSONArray parsedArr=parseInputString(passedData);
		if(inputError!=null) return inputError;
		
		JSONArray responseArr = new JSONArray();
		int[] iarr = {1};
		parsedArr.forEach(obj -> {
			JSONObject validData = null;
			try {
				validData = validator.validate(obj);
			} catch (IOException e) {
				System.console().writer().println(e.getMessage());
				JSONObject jsonErr = new JSONObject();
				jsonErr.put("obj nr", iarr[0]);
				jsonErr.put("errors", e.getMessage());
				responseArr.add(jsonErr);
			}
			if (validData == null) {
				JSONObject jsonErr = new JSONObject();
				jsonErr.put("obj nr", iarr[0]);
				jsonErr.put("errors", validator.getErrors());
				responseArr.add(jsonErr);
			}
			iarr[0]++;
		});
		
		if(responseArr.isEmpty()) {
			return "";
		}
		return responseArr.toJSONString();
	}
}
