package com.hongbao.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hongbao.dal.enums.AccountType;
import com.hongbao.dal.model.UserAccount;



public interface UserAccountMapper {
	UserAccount selectById(@Param("id") Long id);

	// 根据用户id查询 最新用户账号
	UserAccount findUserAccountByUserIdDesc(@Param("userId") Long userId);

	int insert(UserAccount userAccount);

	List<UserAccount> getAccountListByUserId(Long userId);

	List<UserAccount> getAccountListByUserIdAndType(Long userId,
			AccountType type);

	int isExist(@Param("userId") Long userId,
			@Param("accountNo") String accountNo,
			@Param("type") AccountType type);

	int updateUserAccount(UserAccount userAccount);

}
