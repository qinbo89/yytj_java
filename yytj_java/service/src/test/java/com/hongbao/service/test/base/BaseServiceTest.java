package com.hongbao.service.test.base;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hongbao.keepalive.config.ServiceTestConfig;
import com.hongbao.dal.config.ApplicationConfig;
import com.hongbao.dal.config.DalConfig;
import com.hongbao.dal.config.JedisConfig;

@TransactionConfiguration(defaultRollback = false)
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = { DalConfig.class, ServiceTestConfig.class, ApplicationConfig.class, JedisConfig.class })
public class BaseServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    public  Logger logger = LoggerFactory.getLogger(this.getClass()); 
}
