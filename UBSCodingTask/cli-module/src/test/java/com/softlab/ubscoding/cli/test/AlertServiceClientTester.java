/**
 * @author Paweł Włoch ©SoftLab
 * @date 28 sty 2019
 */
package com.softlab.ubscoding.cli.test;

import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;

import com.softlab.ubscoding.cli.rest.AlertServiceComponent;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@TestPropertySource("classpath:application-test.properties")
public class AlertServiceClientTester {

	@Configuration
	@ComponentScan(basePackages = "com.softlab.ubscoding.cli.rest")
	static class Config {
	}

	private final static String LIMIT_QUERY = "/alert?pair={pair}&limit={limit}";
	private final static String DELETE_QUERY = "/alert?pair={pair}";

	@Value("${server.address}")
	private String serverAddress;

	@Value("${server.servlet.context-path}")
	private String servletContextPath;

	@LocalServerPort
	private String port;

	@Autowired
	private AlertServiceComponent service;

	private RestTemplate restTemplate;

	private MockRestServiceServer mockServer;
	private String URL;

	@Before
	public void setUp() {
		URL = "http://" + serverAddress + ":" + port + servletContextPath;
		restTemplate = new RestTemplate();
		RestGatewaySupport gateway = new RestGatewaySupport();
		gateway.setRestTemplate(restTemplate);
		mockServer = MockRestServiceServer.createServer(gateway);
	}

	@Ignore // still don't know how to make Spring to substitute {port} with something else then 0???
	@Test
	public void shouldReturnHttp200OnSetLimit() {
		mockServer.expect(once(), requestTo(URL + LIMIT_QUERY))
				.andRespond(MockRestResponseCreators.withStatus(org.springframework.http.HttpStatus.OK));

		service.putAlert("UBS/UBS", new BigDecimal(100));
		mockServer.verify();
	}

	@Ignore // still don't know how to make Spring to substitute {port} with something else then 0???
	@Test
	public void shouldReturnHttp200OnDeleteLimit() {
		mockServer.expect(once(), requestTo(URL + DELETE_QUERY))
				.andRespond(MockRestResponseCreators.withStatus(org.springframework.http.HttpStatus.OK));

		service.deleteAlert("UBS/UBS");
		mockServer.verify();
	}

}
