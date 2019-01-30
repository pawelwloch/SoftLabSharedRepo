/**
 * @author Paweł Włoch ©SoftLab
 * @date 13 sty 2019
 */
package com.softlab.ubscoding.webserver.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = true)
public class AlertData {

	private String currencyPair;
	private BigDecimal limit;
	private Date timestamp;

}
