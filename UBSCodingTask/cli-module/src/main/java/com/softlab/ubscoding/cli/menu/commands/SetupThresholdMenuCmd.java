/**
 * @author Paweł Włoch ©SoftLab
 * @date 10 sty 2019
 */
package com.softlab.ubscoding.cli.menu.commands;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.softlab.ubscoding.cli.utils.SafeOutput;

@Component
@Order(value = 1)
public class SetupThresholdMenuCmd implements MenuCommand {

	private final static String menuText = "1. Setup a threshold for a price alert";

	@Autowired
	private SafeOutput safeOut;

	@Override
	public void print(StringBuffer buffer) {
		buffer.append(menuText).append('\n');
	}

	/* (non-Javadoc)
	 * @see com.softlab.ubscoding.cli.menu.MenuCommand#execute()
	 */
	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);

		// first which currency pair
		safeOut.printToConsole("Please enter the currency pair: ");
		String pair = scan.nextLine();
		safeOut.printToConsole("Please enter the limit: ");
		int level = 0;
		do {
			try {
				level = scan.nextInt();
			} catch (InputMismatchException ie) {
				safeOut.printToConsole("Please enter natural number");
			}
		} while (level == 0);
		// serviceComponent.setThreshold(pair, level);
	}

}
