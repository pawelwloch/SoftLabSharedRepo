/**
 * @author Paweł Włoch ©SoftLab
 * @date 28 sty 2019
 */
package com.softlab.ubscoding.cli.test;

import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.softlab.ubscoding.cli.rest.AlertServiceComponent;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class AlertServiceClientTester {

	@Configuration
	@ComponentScan(basePackages = "com.softlab.ubscoding.cli.rest")
	public static class RestConfig {
		@Bean
		public RestTemplate getRestTemplate() {
			return new RestTemplate();
		}
	}

	private final static String LIMIT_QUERY = "/alert?pair={pair}&limit={limit}";
	private final static String DELETE_QUERY = "/alert?pair={pair}";

	@Value("${server.address}")
	private String serverAddress;

	@Value("${server.servlet.context-path}")
	private String servletContextPath;

	@LocalServerPort
	private int port;

	@Autowired
	private AlertServiceComponent service;

	@Autowired
	private RestTemplate restTemplate;

	private MockRestServiceServer mockServer;
	private String URL;

	@Before
	public void setUp() {
		mockServer = MockRestServiceServer.bindTo(restTemplate).build();
	}

	@Ignore
	@Test
	public void shouldReturnHttp200OnSetLimit() {
		URL = "http://" + serverAddress + ":" + port + servletContextPath;
		mockServer.expect(once(), requestTo(LIMIT_QUERY)).andRespond(withSuccess());

		service.putAlert("UBS/UBS", new BigDecimal(100));
		mockServer.verify();
	}

	@Ignore
	@Test
	public void shouldReturnHttp200OnDeleteLimit() {
		URL = "http://" + serverAddress + ":" + port + servletContextPath;
		mockServer.expect(once(), requestTo(DELETE_QUERY)).andRespond(withSuccess());

		service.deleteAlert("UBS/UBS");
		mockServer.verify();
	}

}
