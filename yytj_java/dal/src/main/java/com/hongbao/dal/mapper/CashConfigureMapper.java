package com.hongbao.dal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.hongbao.dal.model.CashConfigure;


public interface CashConfigureMapper {

	int insert(CashConfigure cashConfigure);

	int update(CashConfigure cashConfigure);

	CashConfigure getById(@Param("id") String id);

	Integer getCashConfigureListCount(
			@Param("map") Map<String, Object> queryParam);

	List<CashConfigure> getCashConfigureList(@Param("page") Pageable pageable,
			@Param("map") Map<String, Object> queryParam);

}
