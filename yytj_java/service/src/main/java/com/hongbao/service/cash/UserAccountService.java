package com.hongbao.service.cash;

import java.util.List;

import com.hongbao.dal.GlobalResult;
import com.hongbao.dal.enums.AccountType;
import com.hongbao.dal.model.UserAccount;



public interface UserAccountService {
	public GlobalResult addUserAccount(UserAccount userAccount);

	public int updateUserAccount(UserAccount userAccount);

	public List<UserAccount> getAccountListByUserId(Long userId);

	public List<UserAccount> getAccountListByUserIdAndType(Long userId,
			AccountType type);

	public boolean isExist(Long userId, String accountNo, AccountType type);

	/**
	 * 获取用户最新的用户账号
	 * 
	 * @param userId
	 * @return
	 */
	public UserAccount findByUserIdUserAccountDesc(Long userId);

	public UserAccount getUserAccountById(Long id);


}
