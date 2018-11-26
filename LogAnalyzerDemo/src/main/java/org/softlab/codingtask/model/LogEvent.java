/**
 * @author Pawel Wloch ©SoftLab
 * @date 03.11.2018
 */

package org.softlab.codingtask.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="LOG_EVENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames=true)
public class LogEvent {

	@Id private String eventId;
	private long duration;
	private String type;
	private String host;
	private boolean alert;	// "alert" true if any long events take longer than 4ms

}