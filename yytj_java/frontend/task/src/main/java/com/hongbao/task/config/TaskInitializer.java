package com.hongbao.task.config;

import java.io.IOException;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.hongbao.dal.config.ApplicationConfig;
import com.hongbao.dal.config.DalConfig;
import com.hongbao.dal.config.JedisConfig;
import com.hongbao.service.config.ServiceConfig;
import com.hongbao.service.support.ResponseHolderFilter;

public class TaskInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {

        return new Class<?>[] { ApplicationConfig.class, DalConfig.class, ServiceConfig.class, JedisConfig.class };

    }

    static class ConfigApplicationContextInitializer implements
            ApplicationContextInitializer<ConfigurableApplicationContext> {

        private Logger LOG = LoggerFactory.getLogger(ConfigApplicationContextInitializer.class);

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            try {
                environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:filtered.properties"));
                LOG.info("filtered.properties loaded");
            } catch (IOException e) {
                // it's ok if the file is not there. we will just log that info.
                LOG.info("didn't find filtered.properties in classpath so not loading it in the AppContextInitialized");
            }
        }
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { TaskConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        ResponseHolderFilter responseHolderFilter = new ResponseHolderFilter();

        return new Filter[] { characterEncodingFilter, responseHolderFilter// , new XssFilter() 
        };
    }
}