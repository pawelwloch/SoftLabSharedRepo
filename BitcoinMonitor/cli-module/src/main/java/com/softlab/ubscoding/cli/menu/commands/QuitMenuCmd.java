/**
 * @author Paweł Włoch ©SoftLab
 * @date 10 sty 2019
 */
package com.softlab.ubscoding.cli.menu.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 3)
public class QuitMenuCmd implements MenuCommand {

	private final static String menuText = "3. Quit client";

	@Override
	public void print(StringBuffer buffer) {
		buffer.append(menuText).append('\n');
	}

	/* (non-Javadoc)
	 * @see com.softlab.ubscoding.cli.menu.MenuCommand#execute()
	 */
	@Override
	public void execute() {
		System.exit(0);
	}

}
