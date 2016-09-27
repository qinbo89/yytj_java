package com.hongbao.keepalive.config;

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

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"com.hongbao.keepalive"})
@EnableTransactionManagement
@ImportResource("classpath:/META-INF/applicationContext-net.xml")
public class KeepaliveConfig {
	private static Logger logger = LoggerFactory.getLogger(KeepaliveConfig.class);
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