package com.hongbao.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.hongbao.dal.model.AppVersion;

public interface AppVersionMapper {
	AppVersion findCurrentVersion(@Param("os") String osType,
			@Param("appType") String appType);

	Integer getAppVersionListCount();

	List<AppVersion> getAppVersionList(@Param("page") Pageable pageable);

	int addAppVersion(AppVersion appVersion);

	int updateAppVersion(AppVersion appVersion);

	AppVersion getById(@Param("id") Long id);
}