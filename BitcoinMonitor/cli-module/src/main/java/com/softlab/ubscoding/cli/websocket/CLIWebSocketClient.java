/**
 * @author Paweł Włoch ©SoftLab
 * @date 16 sty 2019
 */
package com.softlab.ubscoding.cli.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@Component
public class CLIWebSocketClient {

	@Value("${server.address}")
	private String serverAddress;

	@Value("${server.servlet.context-path}")
	private String servletPath;

	@Value("${server.port}")
	private String port;

	private WebSocketStompClient stompClient;

	@Autowired
	StompSessionHandler cliStompSessionHandler;

	public void connect() {
		// start connection
		List<Transport> transports = new ArrayList<>();
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));

		SockJsClient sockJsClient = new SockJsClient(transports);
		stompClient = new WebSocketStompClient(sockJsClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

		String endpointURL = "ws://" + serverAddress + ":" + port + servletPath + "/monitor-websocket";
		try {
			stompClient.connect(endpointURL, cliStompSessionHandler).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
