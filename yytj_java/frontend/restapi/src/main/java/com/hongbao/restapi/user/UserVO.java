package com.hongbao.restapi.user;

import java.util.List;

import org.springframework.util.StringUtils;

import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserBind;
import com.hongbao.restapi.BaseVO;



public class UserVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	private String phone;

	private String name;

	private String avatar;

	private boolean hasPwd;

	private String nickName; // 昵称

	private String picUrl; // 头像

	private Integer score;

	private String gender;

	private Boolean isFirst;

	private String city; // 城市
	private String occupation; // 职业
	private Long birthday; // 生日
	private String sex; // 性别  1:男  0 女
	private String hobbies; // 爱好
	
	private Long decodeUserId;  //用户id编码


	
	
	/**
	 * @return the decodeUserId
	 */
	public Long getDecodeUserId() {
		return decodeUserId;
	}

	/**
	 * @param decodeUserId the decodeUserId to set
	 */
	public void setDecodeUserId(Long decodeUserId) {
		this.decodeUserId = decodeUserId;
	}

	/** 获取用户登录的 绑定信息 */
	private List<UserBind> userBindList;

	public UserVO() {
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

//	/**
//	 * @return the birthday
//	 */
//	public Date getBirthday() {
//		return birthday;
//	}
//
//	/**
//	 * @param birthday the birthday to set
//	 */
//	public void setBirthday(Date birthday) {
//		this.birthday = birthday;
//	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	

	/**
	 * @return the birthday
	 */
	public Long getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Long birthday) {
		this.birthday = birthday;
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
	 * @return the userBindList
	 */
	public List<UserBind> getUserBindList() {
		return userBindList;
	}

	/**
	 * @param userBindList
	 *            the userBindList to set
	 */
	public void setUserBindList(List<UserBind> userBindList) {
		this.userBindList = userBindList;
	}

	public String getName() {
		return name;
	}

	public String getAvatar() {
		return avatar;
	}

	public boolean isHasPwd() {
		return hasPwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * @param picUrl
	 *            the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param avatar
	 *            the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * @param hasPwd
	 *            the hasPwd to set
	 */
	public void setHasPwd(boolean hasPwd) {
		this.hasPwd = hasPwd;
	}

	/**
	 * @return the isFirst
	 */
	public Boolean getIsFirst() {
		return isFirst;
	}

	/**
	 * @param isFirst
	 *            the isFirst to set
	 */
	public void setIsFirst(Boolean isFirst) {
		this.isFirst = isFirst;
	}

}
