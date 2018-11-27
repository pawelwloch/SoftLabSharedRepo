package org.softlab.codingtask.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.softlab.codingtask.model.JsonLogEvent;
import org.softlab.codingtask.model.LogEvent;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration(classes = { org.softlab.codingtask.config.BatchTestConfiguration.class,
		org.softlab.codingtask.config.BatchConfiguration.class,
		org.softlab.codingtask.components.JobCompletionNotificationListener.class,
		org.softlab.codingtask.components.LogEventProcessor.class })
@RunWith(SpringRunner.class)
@SpringBatchTest
public class LogAnalyzerApplicationTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JsonItemReader<JsonLogEvent> itemReader;

	@Autowired
	public ItemProcessor<JsonLogEvent, LogEvent> itemProcessor;

	/*
	 * public StepExecution getStepExection() { StepExecution execution =
	 * MetaDataInstanceFactory.createStepExecution();
	 * execution.getExecutionContext().putString("test_data.json",
	 * "some context"); return execution; }
	 */ /*
		 * (non-Javadoc)
		 * 
		 * @see junit.framework.TestCase#setUp()
		 */
	/*
	 * @Before public void setUp() throws Exception { List<JsonLogEvent> items =
	 * new ArrayList<>() { "id":"scsmbstgra", "state":"STARTED",
	 * "type":"APPLICATION_LOG","host":"12345", "timestamp":1491377495212};
	 * 
	 * JsonLogEvent event1 = new JsonLogEvent("scsmbstgra", "STARTED",
	 * 1491377495212L, "12345", "APPLICATION_LOG"); JsonLogEvent event1Read =
	 * itemReader.read();
	 * 
	 * assertEquals(event1, event1Read); }
	 */
	/*
	 * @Test public void logEventProcessorTest() throws
	 * UnexpectedInputException, ParseException, Exception {
	 * itemReader.doOpen(); JsonLogEvent logEvent = itemReader.read();
	 * 
	 * JsonLogEvent event1 = new JsonLogEvent("scsmbstgra", "STARTED",
	 * 1491377495212L, "12345", "APPLICATION_LOG"); JsonLogEvent event1 = new
	 * JsonLogEvent("scsmbstgrb", "STARTED", 1491377495212L, "12345",
	 * "APPLICATION_LOG"); JsonLogEvent event1 = new JsonLogEvent("scsmbstgra",
	 * "FINISHED", 1491377495212L, "12345", "APPLICATION_LOG");
	 * 
	 * org.junit.Assert.assertNotNull(logEvent); }
	 */
	/*
	 * @Test public void jsonReaderTest() throws UnexpectedInputException,
	 * ParseException, Exception { // przepuść 1-elementowy stream przez readera
	 * // {"id":"scsmbstgra", "state":"STARTED", //
	 * "type":"APPLICATION_LOG","host":"12345", "timestamp":1491377495212},
	 * JsonLogEvent event1 = new JsonLogEvent("scsmbstgra", "STARTED",
	 * 1491377495212L, "12345", "APPLICATION_LOG"); JsonLogEvent event1Read =
	 * itemReader.read(); JobExecution jobExecution =
	 * jobLauncherTestUtils.launchJob(); Assert.assertEquals("COMPLETED",
	 * jobExecution.getExitStatus().getExitCode()); assertEquals(event1,
	 * event1Read); }
	 */
	@Test
	public void launchJob() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}
}
