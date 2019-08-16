/**
 * @author Pawel Wloch ©SoftLab
 * @date 07.11.2018
 */
package org.softlab.codingtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception {
		if(args.length != 1) {
			System.out.println("Please provide the path to the input file as an input parameter 'logInputFile'.");
		} else { 
			SpringApplication.run(Application.class, args);
		}
	}
}
