package com.hongbao.service.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.hongbao.service.Scanned;

@Configuration
@ComponentScan(basePackageClasses = Scanned.class)
@EnableTransactionManagement
@ImportResource("classpath:/META-INF/applicationContext-service.xml")
public class ServiceConfig {
	private static Logger logger = LoggerFactory.getLogger(ServiceConfig.class);
	@Autowired
	private DataSource dataSource;

	@Bean
	public RestOperations restTemplate() {
		RestTemplate ret = new RestTemplate();
		return ret;
	}

	// @Bean
	// public SchedulerFactoryBean schedulerFactoryBean() {
	// logger.error("init schedulerFactoryBean");
	// SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
	// schedulerFactoryBean.setAutoStartup(true);
	// schedulerFactoryBean
	// .setApplicationContextSchedulerContextKey("applicationContext");
	// schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
	// schedulerFactoryBean.setOverwriteExistingJobs(true);
	//
	// logger.error("dataSource:" + dataSource);
	// // 持久化到数据库
	// schedulerFactoryBean.setDataSource(dataSource);
	//
	// return schedulerFactoryBean;
	// }

}