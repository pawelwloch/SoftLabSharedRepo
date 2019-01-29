/**
 * @author Paweł Włoch ©SoftLab
 * @date 9 sty 2019
 */
package com.softlab.ubscoding.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.softlab.ubscoding.cli.menu.MenuManager;
import com.softlab.ubscoding.cli.websocket.CLIWebSocketClient;

@SpringBootApplication
public class CLIApplication {

	private static Logger LOG = LoggerFactory.getLogger(CLIApplication.class);

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		ConfigurableApplicationContext context = SpringApplication.run(CLIApplication.class, args);

		CLIWebSocketClient wsc = context.getBean(CLIWebSocketClient.class);
		wsc.connect();

		MenuManager manager = context.getBean(MenuManager.class);
		manager.showAndExecuteMenuItems();
	}

}
