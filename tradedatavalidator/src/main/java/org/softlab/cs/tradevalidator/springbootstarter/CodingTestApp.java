/**
 * @author Pawel Wloch @SoftLab
 * @date 11.10.2018
 */
package org.softlab.cs.tradevalidator.springbootstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.softlab.cs.tradevalidator")
public class CodingTestApp {

	public static void main(String[] args) {
		SpringApplication.run(CodingTestApp.class, args);
	}
}
