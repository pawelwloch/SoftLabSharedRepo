/**
 * @author Pawel Wloch @SoftLab
 * @date 13.10.2018
 */

package org.softlab.cs.tradevalidator.validation.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softlab.cs.tradevalidator.springbootstarter.CodingTestApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CodingTestApp.class)
@ComponentScan("org.softlab.cs.tradevalidator")
public class TradeDataValidationServiceTest {

	@Autowired
	TradeDataValidationService service;
	
	@Test
	@Ignore
	public void shouldValidateOK() {
		//service.lo
	}
	
}
