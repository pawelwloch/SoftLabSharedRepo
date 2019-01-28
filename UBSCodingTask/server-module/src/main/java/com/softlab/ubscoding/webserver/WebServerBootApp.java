/**
 * @author Paweł Włoch ©SoftLab
 * @date 11 sty 2019
 */
package com.softlab.ubscoding.webserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebServerBootApp {

	public static void main(String[] args) {
		SpringApplication.run(WebServerBootApp.class, args);
	}

}
