/**
 * @author Paweł Włoch ©SoftLab
 * @date 11 sty 2019
 */
package com.softlab.ubscoding.webserver.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.softlab.ubscoding.webserver.websocket.WebSocketConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ContextConfiguration(classes = { WebSocketConfiguration.class })
public class SpringTest {

	@Test
	public void whenSpringContextIsInstantiated_thenNoExceptions() {
		// When
	}
}
