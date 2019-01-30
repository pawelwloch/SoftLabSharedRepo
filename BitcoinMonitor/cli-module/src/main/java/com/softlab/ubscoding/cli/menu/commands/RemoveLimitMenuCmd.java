/**
 * @author Paweł Włoch ©SoftLab
 * @date 10 sty 2019
 */
package com.softlab.ubscoding.cli.menu.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.softlab.ubscoding.cli.rest.AlertServiceClient;
import com.softlab.ubscoding.cli.utils.UserInterface;

@Component
@Order(value = 2)
public class RemoveLimitMenuCmd implements MenuCommand {

	private final static String menuText = "2. Remove limit";

	@Autowired
	private UserInterface inputInterface;

	@Autowired
	private AlertServiceClient serviceClient;

	@Override
	public void print(StringBuffer buffer) {
		buffer.append(menuText).append('\n');
	}

	@Override
	public void execute() {
		String pair = inputInterface.nextString("Please enter the currency pair: ");
		serviceClient.deleteAlert(pair);
	}
}
