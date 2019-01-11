/**
 * @author Paweł Włoch ©SoftLab
 * @date 10 sty 2019
 */
package com.softlab.ubscoding.cli.menu.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 2)
public class RemoveThresholdMenuCmd implements MenuCommand {

	private final static String menuText = "2. Remove alert";

	@Override
	public void print(StringBuffer buffer) {
		buffer.append(menuText).append('\n');
	}


	/* (non-Javadoc)
	 * @see com.softlab.ubscoding.cli.menu.MenuCommand#execute()
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
