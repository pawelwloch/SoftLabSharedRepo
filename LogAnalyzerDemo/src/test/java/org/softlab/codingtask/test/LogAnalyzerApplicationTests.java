package org.softlab.codingtask.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softlab.codingtask.model.JsonLogEvent;
import org.softlab.codingtask.model.LogEvent;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.test.JobLauncherTestUtils;
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
	public ItemProcessor<JsonLogEvent, LogEvent> itemProcessor;

	@Autowired
	private ResourceLoader resourceLoader;

	private List<JsonLogEvent> inLogEvents = new ArrayList<>();
	private JacksonJsonObjectReader<JsonLogEvent> jsonObjReader = new JacksonJsonObjectReader<>(JsonLogEvent.class);

	@Before
	public void setUpReader() throws Exception {
		// read test resource in json in list
		jsonObjReader.setMapper(new ObjectMapper());
		jsonObjReader.open(resourceLoader.getResource(JSON_TEST_FILE));
	}

	@Test
	public void shouldProcessOneCompleteEvent() throws Exception {
		// given
		JsonLogEvent nextLogEvent;
		while ((nextLogEvent = jsonObjReader.read()) != null) {
			inLogEvents.add(nextLogEvent);
		}
		// when
		LogEvent logEvent = itemProcessor.process(inLogEvents.get(0));
		// then
		Assert.assertNull(logEvent);
		// 1st and 4th line of test_data.json form a LogEvent itemProcessor should return
		Assert.assertEquals(LogEvent.class.getName(), itemProcessor.process(inLogEvents.get(3)).getClass().getName());
	}

	@Test
	public void shouldProperlyReadJsonObjectFromFile() throws UnexpectedInputException, ParseException, Exception {
		// given
		JsonLogEvent fileEvent = new JsonLogEvent("scsmbstgra", "STARTED", 1491377495212L, "APPLICATION_LOG", "host_a");
		// when
		JsonLogEvent readLogEvent = jsonObjReader.read();
		// then
		Assert.assertEquals(fileEvent, readLogEvent);
	}

	@Test
	public void jobTest() throws Exception {
		// when
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();
		// then
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}

}
