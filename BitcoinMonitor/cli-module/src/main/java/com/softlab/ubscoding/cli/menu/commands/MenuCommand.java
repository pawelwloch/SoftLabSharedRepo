/**
 * @author Paweł Włoch ©SoftLab
 * @date 10 sty 2019
 */
package com.softlab.ubscoding.cli.menu.commands;

public interface MenuCommand {

	/**
	 * prints menu item text to the submitted buffer on the UI
	 * 
	 * @param buffer
	 *            holds menu text
	 */
	public void print(StringBuffer buffer);

	/**
	 * executes chosen command
	 */
	public void execute();

}
