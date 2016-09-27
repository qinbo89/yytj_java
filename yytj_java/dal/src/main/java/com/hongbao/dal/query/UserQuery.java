package com.hongbao.dal.query;


import com.hongbao.dal.page.PageInfo;
import com.hongbao.dal.query.BaseQuery;

/**
 * Created by shengshan.tang on 2015/11/27 at 14:13
 */
public class UserQuery<User> extends BaseQuery {

	private Integer type;

	private String accountNo;

	private Integer level;

	private PageInfo<User> pageInfo;

	private String mobile;

	private String email;

	private String qq;

	private Integer status;

	private String pwd;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * @return the pageInfo
	 */
	public PageInfo<User> getPageInfo() {
		return pageInfo;
	}

	/**
	 * @param pageInfo
	 *            the pageInfo to set
	 */
	public void setPageInfo(PageInfo<User> pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
