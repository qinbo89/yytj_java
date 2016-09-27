package com.hongbao.dal.mapper;

import java.util.List;

import com.hongbao.dal.model.UserStat;


public interface UserStatMapper {

	int insert(UserStat record);

	List<UserStat> stat(UserStat record);

}
