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
			System.out.println("Pass the path to the input file as parameter.");
		} else { 
			SpringApplication.run(Application.class, args);
		}
	}
}
