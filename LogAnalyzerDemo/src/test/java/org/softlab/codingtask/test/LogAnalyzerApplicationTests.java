package org.softlab.codingtask.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softlab.codingtask.model.JsonLogEvent;
import org.softlab.codingtask.model.LogEvent;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBatchTest
@ContextConfiguration(classes = { org.softlab.codingtask.config.BatchTestConfiguration.class,
		org.softlab.codingtask.config.BatchConfiguration.class,
		org.softlab.codingtask.components.JobCompletionNotificationListener.class,
		org.softlab.codingtask.components.LogEventProcessor.class })
@RunWith(SpringRunner.class)
public class LogAnalyzerApplicationTests {

	private final static String JSON_TEST_FILE = "classpath:test_data.json";

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JsonItemReader<JsonLogEvent> itemReader;

	@Autowired
	public ItemProcessor<JsonLogEvent, LogEvent> itemProcessor;

	@Autowired
	private ResourceLoader resourceLoader;

	private List<JsonLogEvent> inLogEvents = new ArrayList<>();

	public StepExecution getStepExection() {
		StepExecution execution = MetaDataInstanceFactory.createStepExecution();
		execution.getExecutionContext().putString("test_data.json", "some context");
		return execution;
	}

	@Before
	public void setUpReader() throws Exception {
		// read test resource in json in list
		JacksonJsonObjectReader<JsonLogEvent> jsonObjReader = new JacksonJsonObjectReader<>(JsonLogEvent.class);
		jsonObjReader.setMapper(new ObjectMapper());
		jsonObjReader.open(resourceLoader.getResource(JSON_TEST_FILE));

		JsonLogEvent nextLogEvent;
		while ((nextLogEvent = jsonObjReader.read()) != null) {
			inLogEvents.add(nextLogEvent);
		}
	}

	@Test
	public void logEventProcessingTest() throws Exception {
		Assert.assertNull(itemProcessor.process(inLogEvents.get(0)));
		// 1st and 4th line of test_data.json form a LogEvent itemProcessor should return
		Assert.assertEquals(LogEvent.class.getName(), itemProcessor.process(inLogEvents.get(3)).getClass().getName());
		
	}

	@Test
	@Ignore
	public void jsonReaderTest() throws UnexpectedInputException, ParseException, Exception {
		// przepuść 1-elementowy stream przez readera
		// {"id":"scsmbstgra", "state":"STARTED",
		// "type":"APPLICATION_LOG","host":"12345", "timestamp":1491377495212},
		// JsonLogEvent("scsmbstgra", "STARTED", 1491377495212L, "12345", "APPLICATION_LOG");
		// setUpReader();
		JsonLogEvent event1Read = itemReader.read();
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();
		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
	}

	@Test
	public void launchJob() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	/*
	 * @Before public void setUp() throws Exception { List<JsonLogEvent> items = new ArrayList<>() { "id":"scsmbstgra",
	 * "state":"STARTED", "type":"APPLICATION_LOG","host":"12345", "timestamp":1491377495212};
	 * 
	 * JsonLogEvent event1 = new JsonLogEvent("scsmbstgra", "STARTED", 1491377495212L, "12345", "APPLICATION_LOG");
	 * JsonLogEvent event1Read = itemReader.read();
	 * 
	 * assertEquals(event1, event1Read); }
	 */
	/*
	 * @Test public void logEventProcessorTest() throws UnexpectedInputException, ParseException, Exception {
	 * itemReader.doOpen(); JsonLogEvent logEvent = itemReader.read();
	 * 
	 * JsonLogEvent event1 = new JsonLogEvent("scsmbstgra", "STARTED", 1491377495212L, "12345", "APPLICATION_LOG");
	 * JsonLogEvent event1 = new JsonLogEvent("scsmbstgrb", "STARTED", 1491377495212L, "12345", "APPLICATION_LOG");
	 * JsonLogEvent event1 = new JsonLogEvent("scsmbstgra", "FINISHED", 1491377495212L, "12345", "APPLICATION_LOG");
	 * 
	 * org.junit.Assert.assertNotNull(logEvent); }
	 */

}
