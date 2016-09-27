package com.hongbao.keepalive.config;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.inject.Inject;

import org.junit.Before;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.hongbao.dal.config.ApplicationConfig;
import com.hongbao.dal.config.DalConfig;
import com.hongbao.restapi.base.config.WebMvcConfig;

// @RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationConfig.class, DalConfig.class,  WebMvcConfig.class })
public abstract class WebAppConfigurationAware {

	@Inject
	protected WebApplicationContext wac;
	protected MockMvc mockMvc;

	@Before
	public void before() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}

}
