/**
 * @author Paweł Włoch ©SoftLab
 * @date 27 lis 2018
 */
package org.softlab.codingtask.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class JPADataSourceConfiguration extends DefaultBatchConfigurer {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private DataSource dataSource;

	@Override
	public PlatformTransactionManager getTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		transactionManager.setDataSource(dataSource);
		transactionManager.setJpaDialect(jpaDialect());
		return transactionManager;
	}

	@Bean
	public JpaDialect jpaDialect() {
		JpaDialect jpaDialect = new org.springframework.orm.jpa.vendor.HibernateJpaDialect();
		return jpaDialect;
	}

}
