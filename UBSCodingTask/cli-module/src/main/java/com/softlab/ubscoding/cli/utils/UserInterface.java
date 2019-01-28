/**
 * @author Paweł Włoch ©SoftLab
 * @date 28 sty 2019
 */
package com.softlab.ubscoding.cli.utils;

import java.math.BigDecimal;

public interface UserInterface {

	public String nextString(String textToDisplay);

	public BigDecimal nextBigDecimal(String testToDisplay);

}
