/**
 * @author Pawel Wloch ©SoftLab
 * @date 07.11.2018
 */
package org.softlab.codingtask.components;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softlab.codingtask.model.LogEvent;
import org.softlab.codingtask.model.LogEventRepository;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	
	@Autowired
	private LogEventRepository logEventRepository;

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!JOB FINISHED! EOF input file.");

			List<LogEvent> findAll = logEventRepository.findAll();
			findAll.forEach(event -> log.info("object in the database: " + event.toString()));
		}
	}
}
