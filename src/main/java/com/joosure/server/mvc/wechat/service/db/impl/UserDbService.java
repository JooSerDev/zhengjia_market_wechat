package com.joosure.server.mvc.wechat.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joosure.server.mvc.wechat.dao.database.UserDao;
import com.joosure.server.mvc.wechat.entity.pojo.User;
import com.joosure.server.mvc.wechat.entity.pojo.UserWechatInfo;
import com.joosure.server.mvc.wechat.service.db.IUserDbService;

@Service("userDbService")
public class UserDbService implements IUserDbService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public User getUserByOpenid(String openid) {
		return userDao.getUserByOpenid(openid);
	}

	@Override
	public User getUserByMobile(String mobile) {
		return userDao.getUserByMobile(mobile);
	}

	@Override
	public List<User> getUsersOrderByItemNumTop8() {
		return userDao.getUsersOrderByItemNumTop8();
	}

	@Override
	public void saveUserWechatInfo(UserWechatInfo userWechatInfo) {
		userDao.saveUserWechatInfo(userWechatInfo);
	}

	@Override
	public void updateUserWechatInfo(UserWechatInfo userWechatInfo) {
		userDao.updateUserWechatInfo(userWechatInfo);
	}

	@Override
	public UserWechatInfo getUserWechatInfoByOpenid(String openid) {
		return userDao.getUserWechatInfoByOpenid(openid);
	}

}
