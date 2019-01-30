/**
 * @author Paweł Włoch ©SoftLab
 * @date 10 sty 2019
 */
package com.softlab.ubscoding.cli.menu.commands;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.softlab.ubscoding.cli.rest.AlertServiceClient;
import com.softlab.ubscoding.cli.utils.UserInterface;

@Component
@Order(value = 1)
public class SetupLimitMenuCmd implements MenuCommand {

	private final static String menuText = "1. Setup a threshold for a price alert";

	@Autowired
	private UserInterface inputInterface;

	@Autowired
	private AlertServiceClient serviceClient;

	@Override
	public void print(StringBuffer buffer) {
		buffer.append(menuText).append('\n');
	}

	/* (non-Javadoc)
	 * @see com.softlab.ubscoding.cli.menu.MenuCommand#execute()
	 */
	@Override
	public void execute() {
		String pair = inputInterface.nextString("Please enter the currency pair: ");
		BigDecimal limit = inputInterface.nextBigDecimal("Please enter the limit: ");
		serviceClient.putAlert(pair, limit);
	}

}
