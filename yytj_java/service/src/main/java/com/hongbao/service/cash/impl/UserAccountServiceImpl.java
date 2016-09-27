package com.hongbao.service.cash.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongbao.dal.GlobalResult;
import com.hongbao.dal.enums.AccountType;
import com.hongbao.dal.mapper.UserAccountMapper;
import com.hongbao.dal.model.UserAccount;
import com.hongbao.service.cash.UserAccountService;

@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {
	private static Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);
	@Autowired
	private UserAccountMapper userAccountMapper;

	@Override
	public GlobalResult addUserAccount(UserAccount userAccount) {
		if (userAccount != null) {
			boolean isExist = isExist(userAccount.getUserId(), userAccount.getAccountNo(), userAccount.getType());
			if (!isExist) {
				int rows = userAccountMapper.insert(userAccount);
				if (rows > 0) {
					return GlobalResult.success;
				} else {
					logger.warn("新增用户提现账号失败");
					return GlobalResult.error;
				}
			} else {
				logger.warn("用户提现账号重复");
				return GlobalResult.repeat;
			}
		}
		return GlobalResult.nullValue;
	}

	@Override
	public List<UserAccount> getAccountListByUserId(Long userId) {
		return userAccountMapper.getAccountListByUserId(userId);
	}

	@Override
	public List<UserAccount> getAccountListByUserIdAndType(Long userId, AccountType type) {
		return userAccountMapper.getAccountListByUserIdAndType(userId, type);
	}

	@Override
	public boolean isExist(Long userId, String accountNo, AccountType type) {
		return userAccountMapper.isExist(userId, accountNo, type) > 0;
	}

	@Override
	public int updateUserAccount(UserAccount userAccount) {
		return userAccountMapper.updateUserAccount(userAccount);
	}

	@Override
	public UserAccount findByUserIdUserAccountDesc(Long userId) {
		
		return userAccountMapper.findUserAccountByUserIdDesc(userId);
	}

	@Override
	public UserAccount getUserAccountById(Long id) {
		return userAccountMapper.selectById(id);
	}

}
