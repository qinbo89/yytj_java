package com.hongbao.dal.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;

import com.hongbao.dal.mapper.AppCtrMapper;
import com.hongbao.dal.mapper.AppDownloadMapper;
import com.hongbao.dal.mapper.AppVersionMapper;
import com.hongbao.dal.mapper.CashApplyMapper;
import com.hongbao.dal.mapper.ClickLogMapper;
import com.hongbao.dal.mapper.DuobaoMapper;
import com.hongbao.dal.mapper.TryAppMapper;
import com.hongbao.dal.mapper.UserAccountMapper;
import com.hongbao.dal.mapper.UserAppMapper;
import com.hongbao.dal.mapper.UserBindMapper;
import com.hongbao.dal.mapper.UserDuobaoMapper;
import com.hongbao.dal.mapper.UserLocusMapper;
import com.hongbao.dal.mapper.UserMapper;
import com.hongbao.dal.mapper.UserRelationMapper;
import com.hongbao.dal.mapper.UserScoreMapper;
import com.hongbao.dal.mapper.UserStatMapper;
import com.hongbao.dal.mybatis.IdTypeHandler;

@Configuration
@ImportResource("classpath:/META-INF/applicationContext-dal.xml")
public class DalConfig {

	@Value("classpath:config/MapperConfig.xml")
	Resource mybatisMapperConfig;

	@Autowired
	DataSource dataSource;

	@Bean
	public UserMapper userMapper() throws Exception {
		return newMapperFactoryBean(UserMapper.class).getObject();
	}

	@Bean
	public AppDownloadMapper appDownloadMapper() throws Exception {
		return newMapperFactoryBean(AppDownloadMapper.class).getObject();
	}

	@Bean
	public CashApplyMapper cashApplyMapper() throws Exception {
		return newMapperFactoryBean(CashApplyMapper.class).getObject();
	}

	@Bean
	public UserStatMapper userStatMapper() throws Exception {
		return newMapperFactoryBean(UserStatMapper.class).getObject();
	}

	@Bean
	public UserScoreMapper userScoreMapper() throws Exception {
		return newMapperFactoryBean(UserScoreMapper.class).getObject();
	}

	@Bean
	public UserAccountMapper userAccountMapper() throws Exception {
		return newMapperFactoryBean(UserAccountMapper.class).getObject();
	}

	@Bean
	public UserAppMapper serAppMapper() throws Exception {
		return newMapperFactoryBean(UserAppMapper.class).getObject();
	}

	@Bean
	public UserRelationMapper userRelationMapper() throws Exception {
		return newMapperFactoryBean(UserRelationMapper.class).getObject();
	}

	@Bean
	public TryAppMapper tryAppMapper() throws Exception {
		return newMapperFactoryBean(TryAppMapper.class).getObject();
	}

	@Bean
	public ClickLogMapper clickLogMapper() throws Exception {
		return newMapperFactoryBean(ClickLogMapper.class).getObject();
	}

	@Bean
	public UserLocusMapper userLocusMapper() throws Exception {
		return newMapperFactoryBean(UserLocusMapper.class).getObject();
	}

	@Bean
	public UserBindMapper userBindMapper() throws Exception {
		return newMapperFactoryBean(UserBindMapper.class).getObject();
	}

	@Bean
	public AppVersionMapper appVersionMapper() throws Exception {
		return newMapperFactoryBean(AppVersionMapper.class).getObject();
	}

	@Bean
	public AppCtrMapper appCtrMapper() throws Exception {
		return newMapperFactoryBean(AppCtrMapper.class).getObject();
	}
	
	@Bean
	public DuobaoMapper duobaoMapper() throws Exception {
	    return newMapperFactoryBean(DuobaoMapper.class).getObject();
	}
	@Bean
	public UserDuobaoMapper userDuobaoMapper() throws Exception {
	    return newMapperFactoryBean(UserDuobaoMapper.class).getObject();
	}

	<T> MapperFactoryBean<T> newMapperFactoryBean(Class<T> clazz)
			throws Exception {
		MapperFactoryBean<T> b = new MapperFactoryBean<T>();
		b.setMapperInterface(clazz);
		b.setSqlSessionFactory(sqlSessionFactory());
		return b;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		fb.setConfigLocation(mybatisMapperConfig);
		fb.setDataSource(dataSource);
		fb.setTypeAliases(new Class<?>[] { IdTypeHandler.class });
		return fb.getObject();
	}
}
