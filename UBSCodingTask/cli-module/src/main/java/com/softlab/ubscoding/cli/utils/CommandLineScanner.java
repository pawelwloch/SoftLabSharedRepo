/**
 * @author Paweł Włoch ©SoftLab
 * @date 28 sty 2019
 */
package com.softlab.ubscoding.cli.utils;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandLineScanner implements UserInterface {

	@Autowired
	private SafeOutput safeOut;

	@Override
	public String nextString(String textToDisplay) {
		String pair = "";
		
		boolean exception;
		synchronized (safeOut) {
			do {
				safeOut.printToConsole(textToDisplay);
				exception = false;
				try {
					Scanner scan = new Scanner(System.in);
					pair = scan.nextLine();
				} catch (Exception e) {
					exception = true;
				}
			} while (exception);
		}

		return pair;
	}

	@Override
	public BigDecimal nextBigDecimal(String textToDisplay) {
		BigDecimal limit = new BigDecimal(0);

		boolean exception;
		synchronized (safeOut) {
			do {
				safeOut.printToConsole(textToDisplay);
				exception = false;
				try {
					Scanner scan = new Scanner(System.in);
					limit = scan.nextBigDecimal();
				} catch (InputMismatchException ie) {
					safeOut.printToConsole("Please enter natural number");
					exception = true;
				} catch (Exception e) {
					exception = true;
				}
			} while (exception);
		}
		return limit;
	}

}
