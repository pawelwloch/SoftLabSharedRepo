/**
 * @author Paweł Włoch ©SoftLab
 * @date 10 sty 2019
 */
package com.softlab.ubscoding.cli.rest;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AlertServiceComponent implements AlertServiceClient {

	private static Logger LOG = LoggerFactory.getLogger(AlertServiceComponent.class);

	private String URL;

	private RestTemplate restTemplate;

	private final static String LIMIT_QUERY = "/alert?pair={pair}&limit={limit}";
	private final static String DELETE_QUERY = "/alert?pair={pair}";

	@Value("${server.address}")
	private String serverAddress;

	@Value("${server.servlet.context-path}")
	private String servletContextPath;

	@Value("${server.port}")
	private String port;

	public AlertServiceComponent(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@PostConstruct
	public void init() {
		URL = "http://" + serverAddress + ":" + port + servletContextPath;
	}

	@Override
	public void putAlert(String currencyPair, BigDecimal limit) {
		restTemplate.put(URL + LIMIT_QUERY, null, currencyPair, limit.toPlainString());
	}

	@Override
	public void deleteAlert(String currencyPair) {
		restTemplate.delete(URL + DELETE_QUERY, currencyPair);

	}
}
