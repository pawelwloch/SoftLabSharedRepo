/**
 * @author Pawel Wloch @SoftLab
 * @date 11.10.2018
 */
package org.softlab.cs.tradevalidator.controller;

import java.io.IOException;

import org.softlab.cs.tradevalidator.validation.service.TradeDataValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradeDataValidationController {

	@Autowired
	private TradeDataValidationService validatorService;
	
	/**
	 * @param tradeData
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(path="/tradedata", 
					method=RequestMethod.POST, 
					headers={"Content-Type=application/json","Accept=text/xml, application/json"})
	public @ResponseBody Object validateTradeData(@RequestBody String tradeData) throws IOException {
		return validatorService.validateTradeData(tradeData);
	}
}
