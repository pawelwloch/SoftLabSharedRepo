/**
 * @author Paweł Włoch ©SoftLab
 * @date 10 sty 2019
 */
package com.softlab.ubscoding.cli.utils;

import org.springframework.stereotype.Component;

@Component
public class SafeOutput {

	public void printToConsole(String s) {
		synchronized (this) {
			System.out.println(s);
		}
	}
}
