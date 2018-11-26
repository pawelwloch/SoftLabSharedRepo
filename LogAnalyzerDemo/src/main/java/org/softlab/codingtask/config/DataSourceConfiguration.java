/**
 * @author Paweł Włoch ©SoftLab
 * @date 27 lis 2018
 */
package org.softlab.codingtask.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;


public class DataSourceConfiguration {

	@Autowired
	public PlatformTransactionManager transactionManager;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private DataSource dataSource;

	@Bean(name = "jpaTransactionManager")
	@Primary
	public PlatformTransactionManager transactionManager() {
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
