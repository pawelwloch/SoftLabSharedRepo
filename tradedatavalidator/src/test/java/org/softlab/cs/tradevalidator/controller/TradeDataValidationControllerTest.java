/**
 * @author Pawel Wloch @SoftLab
 * @date 12.10.2018
 */

package org.softlab.cs.tradevalidator.controller;

import static org.junit.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softlab.cs.tradevalidator.TestConfig;
import org.softlab.cs.tradevalidator.validation.service.TradeDataValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes=TestConfig.class)
public class TradeDataValidationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
    private TradeDataValidationService validatorService;
	
	@Test
    public void controllerShouldReturnHello() throws Exception {

		JSONObject json = new JSONObject();
		json.put("test", "data");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/tradedata")
				.accept(MediaType.APPLICATION_JSON).content(json.toJSONString())
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}
