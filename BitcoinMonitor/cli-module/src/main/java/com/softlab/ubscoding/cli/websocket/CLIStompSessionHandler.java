/**
 * @author Paweł Włoch ©SoftLab
 * @date 16 sty 2019
 */
package com.softlab.ubscoding.cli.websocket;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import com.softlab.ubscoding.cli.model.AlertData;
import com.softlab.ubscoding.cli.utils.SafeOutput;

@Component
public class CLIStompSessionHandler extends StompSessionHandlerAdapter {

	private static Logger LOG = LoggerFactory.getLogger(CLIStompSessionHandler.class);

	@Autowired
	private SafeOutput safeOutput;

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		session.subscribe("/topic/limit-alerts", this);
	}

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return AlertData.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		LOG.debug("handle frame containing payload:" + payload.toString());
		AlertData alertData = (AlertData) payload;
		safeOutput.printToConsole(alertData.toString());
	}

	@Override
	public void handleTransportError(StompSession session, Throwable ex) {
		LOG.error(ex.getMessage());
	}
}
