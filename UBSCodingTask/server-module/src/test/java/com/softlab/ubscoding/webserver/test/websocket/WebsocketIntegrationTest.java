/**
 * @author Paweł Włoch ©SoftLab
 * @date 17 sty 2019
 */
package com.softlab.ubscoding.webserver.test.websocket;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.softlab.ubscoding.webserver.model.AlertData;
import com.softlab.ubscoding.webserver.model.LimitsWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class WebsocketIntegrationTest {

	private static Logger LOG = LoggerFactory.getLogger(WebsocketIntegrationTest.class);

	private static final CurrencyPair CURRENCY_PAIR = CurrencyPair.BTC_PLN;

	private static final String ALERT_TOPIC = "/topic/limit-alerts";

	@Value("${server.address}")
	private String serverAddress;

	@LocalServerPort
	private int port;

	@Value("${server.servlet.context-path}")
	private String servletContextPath;

	@Autowired
	private LimitsWrapper limitsData;

	private String URL;
	private WebSocketStompClient stompClient;
	private volatile boolean dataReceived = false;


	@Before
	public void setup() throws Exception {
		URL = "ws://" + serverAddress + ":" + port + servletContextPath + "/monitor-websocket";

		List<Transport> transports = new ArrayList<>();
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		SockJsClient sockJsClient = new SockJsClient(transports);

		this.stompClient = new WebSocketStompClient(sockJsClient);
		this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		// set limit to 0 for a basic currency
		limitsData.setLimitForCurrPair(CURRENCY_PAIR, new BigDecimal(0));
	}

	@Test
	public void shouldSubscribeAndReceiveAlert() throws InterruptedException, ExecutionException, TimeoutException {
		StompSessionHandler handler = new StompSessionHandlerAdapter() {
			@Override
			public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
				session.subscribe(ALERT_TOPIC, new StompFrameHandler() {
					@Override
					public Type getPayloadType(StompHeaders headers) {
						return AlertData.class;
					}

					@Override
					public void handleFrame(StompHeaders headers, Object payload) {
						LOG.debug(payload.toString());
						AlertData alert = (AlertData) payload;
						assertTrue(alert.getCurrencyPair().equals(CURRENCY_PAIR.toString()));
						System.out.println("received alert: " + alert.toString());
						LOG.debug("received alert:" + alert.toString());
						dataReceived = true;
					}
				});
				LOG.debug("succesfully subscribed to topic on socket");
			}
		};
		StompSession stompSession = stompClient.connect(URL, handler).get();
		assertTrue(stompSession.isConnected());
		LOG.debug("succesfully connected to socket");
		// TODO add automatic test failure after waiting e.g. 5 secs
		while (!dataReceived) {
			Thread.sleep(1000);
		}
		stompSession.disconnect();
	}

}
