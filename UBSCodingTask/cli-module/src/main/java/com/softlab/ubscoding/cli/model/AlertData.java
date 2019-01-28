/**
 * @author Paweł Włoch ©SoftLab
 * @date 13 sty 2019
 */
package com.softlab.ubscoding.cli.model;

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
	private Double limit;
	private Date timestamp;

}
