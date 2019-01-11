/**
 * @author Paweł Włoch ©SoftLab
 * @date 9 sty 2019
 */
package com.softlab.ubscoding.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.softlab.ubscoding.cli.menu.MenuManager;
import com.softlab.ubscoding.cli.websocket.WebSocketServer;

@SpringBootApplication
public class CLIApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(CLIApplication.class);

	@Autowired
	private MenuManager menuManager;

	@Autowired
	private WebSocketServer wss;

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(CLIApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		LOG.info("EXECUTING : command line runner");
		// start server in separate thread
		wss.run();
		menuManager.showAndExecuteMenuItems();
	}

}
