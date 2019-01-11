/**
 * @author Paweł Włoch ©SoftLab
 * @date 11 sty 2019
 */
package com.softlab.ubscoding.cli.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WebSocketServer implements Runnable {

	private static Logger LOG = LoggerFactory.getLogger(WebSocketServer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		LOG.info("web socket server started");

	}

}
