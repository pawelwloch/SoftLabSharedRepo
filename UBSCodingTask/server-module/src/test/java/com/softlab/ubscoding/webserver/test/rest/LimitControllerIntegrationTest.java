/**
 * @author Paweł Włoch ©SoftLab
 * @date 25 sty 2019
 */
package com.softlab.ubscoding.webserver.test.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.softlab.ubscoding.webserver.model.LimitsContainer;
import com.softlab.ubscoding.webserver.rest.LimitController;

@RunWith(SpringRunner.class)
@WebMvcTest(LimitController.class)
public class LimitControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private LimitsContainer limitHolder;

	@Test
	public void shouldReturnBadRequestOnMissingAlertParams() throws Exception {
		mvc.perform(put("/alert")).andExpect(status().isBadRequest());
	}

	@Test
	public void shouldReturnBadRequestOnMissingDeleteParams() throws Exception {
		mvc.perform(delete("/alert")).andExpect(status().isBadRequest());
	}

	@Test
	public void shouldReturnOKOnProperAlertParams() throws Exception {
		mvc.perform(put("/alert?pair=BTC/PLN&limit=200")).andExpect(status().isOk());
	}

	@Test
	public void shouldReturnOKOnProperDeleteParam() throws Exception {
		mvc.perform(delete("/alert?pair=BTC/PLN")).andExpect(status().isOk());
	}

}
