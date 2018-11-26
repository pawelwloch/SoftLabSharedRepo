/**
 * @author Paweł Włoch ©SoftLab
 * @date 21 lis 2018
 */
package org.softlab.codingtask.test;

import java.net.MalformedURLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource("classpath:test.properties")
@EnableJpaRepositories(basePackages = { "org.softlab.codingtask" })
public class BatchTestConfiguration {

	@Autowired
	private Environment env;

	@Value("org/springframework/batch/core/schema-drop-hsqldb.sql")
	private Resource dropRepositoryTables;

	@Value("org/springframework/batch/core/schema-hsqldb.sql")
	private Resource dataRepositorySchema;

	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) throws MalformedURLException {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();

		databasePopulator.addScript(dropRepositoryTables);
		databasePopulator.addScript(dataRepositorySchema);
		databasePopulator.setIgnoreFailedDrops(true);

		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(databasePopulator);

		return initializer;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(getDataSource());
		em.setPackagesToScan(new String[] { "org.softlab.codingtask.model" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		properties.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbc.JDBCDriver");
		properties.setProperty("hibernate.connection.url", "jdbc:hsqldb:file:./logAnalyzerTestDb");
		properties.setProperty("hibernate.connection.username", "SA");
		return properties;
	}

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.user"));

		return dataSource;
	}

	@Bean(name = "jpaTransactionManager")
	@Primary
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		transactionManager.setDataSource(getDataSource());
		transactionManager.setJpaDialect(jpaDialect());
		return transactionManager;
	}

	@Bean
	public JpaDialect jpaDialect() {
		JpaDialect jpaDialect = new org.springframework.orm.jpa.vendor.HibernateJpaDialect();
		return jpaDialect;
	}

	/*
	 * @Bean public TransactionProxyFactoryBean baseProxy() throws Exception {
	 * TransactionProxyFactoryBean transactionProxyFactoryBean = new
	 * TransactionProxyFactoryBean(); Properties transactionAttributes = new
	 * Properties(); transactionAttributes.setProperty("*",
	 * "PROPAGATION_REQUIRED");
	 * transactionProxyFactoryBean.setTransactionAttributes(
	 * transactionAttributes);
	 * transactionProxyFactoryBean.setTarget(getJobRepository());
	 * transactionProxyFactoryBean.setTransactionManager(transactionManager());
	 * return transactionProxyFactoryBean; }
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.core.configuration.annotation.BatchConfigurer#
	 * getJobRepository()
	 */
	/*
	 * @Bean public JobRepository getJobRepository() throws Exception {
	 * JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
	 * factory.setDataSource(setDataSource());
	 * factory.setTransactionManager(transactionManager()); //
	 * JobRepositoryFactoryBean's methods Throws Generic Exception, // it would
	 * have been better to have a specific one // factory.afterPropertiesSet();
	 * return factory.getObject(); }
	 */
}
