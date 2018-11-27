/**
 * @author Pawel Wloch ©SoftLab
 * @date 07.11.2018
 */
package org.softlab.codingtask.config;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softlab.codingtask.components.JobCompletionNotificationListener;
import org.softlab.codingtask.model.JsonLogEvent;
import org.softlab.codingtask.model.LogEvent;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootConfiguration
@Import(JPADataSourceConfiguration.class)
public class BatchConfiguration {

	private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

	@Value("${logInputFile}")
	private String pathToInputLogDataJsonFile;
	
	@Autowired
	public PlatformTransactionManager transactionManager;

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public ItemProcessor<JsonLogEvent, LogEvent> logEventProcessor;

	@Bean
    public JsonItemReader<JsonLogEvent> jsonItemReader() {
       ObjectMapper objectMapper = new ObjectMapper();
       objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
       objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
       JacksonJsonObjectReader<JsonLogEvent> jsonObjectReader = new JacksonJsonObjectReader<>(JsonLogEvent.class);
       jsonObjectReader.setMapper(objectMapper);

       return new JsonItemReaderBuilder<JsonLogEvent>()
                     .jsonObjectReader(jsonObjectReader)
                     .resource(new FileSystemResource(pathToInputLogDataJsonFile))
                     .name("logEventJsonItemReader")
                     .build();
    }
    
    @Bean
    public JpaItemWriter<LogEvent> writer() {
    	JpaItemWriter<LogEvent> writer = new JpaItemWriter<>();
    	writer.setEntityManagerFactory(entityManagerFactory);
    	return writer;
    }
    
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }
    
    @Bean
    public Step step1(JpaItemWriter<LogEvent> writer) {
        return stepBuilderFactory.get("step1")
				.transactionManager(transactionManager)
				.<JsonLogEvent, LogEvent>chunk(1)
            .reader(jsonItemReader())
				.processor(logEventProcessor)
            .writer(writer)
            .build();
    }

}
