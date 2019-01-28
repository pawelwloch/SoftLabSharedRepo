/**
 * @author Paweł Włoch ©SoftLab
 * @date 10 sty 2019
 */
package com.softlab.ubscoding.cli.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.softlab.ubscoding.cli.menu.commands.MenuCommand;
import com.softlab.ubscoding.cli.utils.SafeOutput;

@Component
public class CLIMenuManager implements MenuManager {

	@Autowired
	private SafeOutput safeOut;

	@Autowired
	private List<MenuCommand> autowiredMenu;
	private ArrayList<MenuCommand> menu = new ArrayList<>();
	private String menuString;

	@PostConstruct
	private void initializeOrderedMenu() {
		autowiredMenu.forEach(menuItem -> menu.add(menuItem));
		autowiredMenu = null;

		StringBuffer menuBuffer = new StringBuffer();
		menu.forEach(menuItem -> menuItem.print(menuBuffer));
		menuString = menuBuffer.toString();
	}

	/* (non-Javadoc)
	 * @see com.softlab.ubscoding.cli.menu.MenuManager#displayMenu()
	 */
	@Override
	public void displayMenu() {
		safeOut.printToConsole(menuString);
	}

	/* (non-Javadoc)
	 * @see com.softlab.ubscoding.cli.menu.MenuManager#getUserInput()
	 */
	@Override
	public void showAndExecuteMenuItems() {
		Scanner scan = new Scanner(System.in);

		while (true) {
			displayMenu();
			int choice = 0;
			boolean exception;
			do {
				exception = false;
				try {
					choice = scan.nextInt();
				} catch (Exception e) {
					exception = true;
				}
			} while (exception);

			// 0th element is a header
			if (choice < 1 || choice >= menu.size()) {
				safeOut.printToConsole("Please enter matching number.");
			} else {
				menu.get(choice).execute();
			}
		}
	}

}
