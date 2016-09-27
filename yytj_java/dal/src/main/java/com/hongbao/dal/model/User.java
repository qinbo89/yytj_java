package com.hongbao.dal.model;

import java.util.Date;


import com.hongbao.dal.Archivable;
import com.hongbao.dal.BaseEntityImpl;




public class User extends BaseEntityImpl implements  Archivable {


	private static final long serialVersionUID = -826024320466145566L;
	private String name;
	/**
	 * 是否后台账号，TODO 先简单实现了，有必要在支持UserRole
	 */
	private Boolean admin;
	private String avatar;
	private String email;
	private String loginname;
	private String phone;
	private String password;
	private Boolean archive;
	private String shopId;
	private String outNick;
	private String source;
	private String outUserId;
	private String uuid;
	private String treePath;

	private String nickname; // 昵称
	private String picUri; // 头像id
	private String city; // 城市
	private String occupation; // 职业
	private Date birthday; // 生日
	private String sex; // 性别
	private String hobbies; // 爱好
	private String unionid;
	private String openId;
	private int type;
	
	/**
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * @param openId the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @return the unionid
	 */
	public String getUnionid() {
		return unionid;
	}

	/**
	 * @param unionid the unionid to set
	 */
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	@SuppressWarnings("unused")
	private Long decodeUserId;

	



	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * @param occupation
	 *            the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the hobbies
	 */
	public String getHobbies() {
		return hobbies;
	}

	/**
	 * @param hobbies
	 *            the hobbies to set
	 */
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	/**
	 * @return the picUri
	 */
	public String getPicUri() {
		return picUri;
	}

	/**
	 * @param picUri
	 *            the picUri to set
	 */
	public void setPicUri(String picUri) {
		this.picUri = picUri;
	}

	

	public String getName() {
		return name;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getAdmin() {
		if (admin == null)
			return false;
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	

	public Boolean getArchive() {
		return archive;
	}

	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getOutNick() {
		return outNick;
	}

	public void setOutNick(String outNick) {
		this.outNick = outNick;
	}

	public String getOutUserId() {
		return outUserId;
	}

	public void setOutUserId(String outUserId) {
		this.outUserId = outUserId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	

}
