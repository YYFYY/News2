package com.SpringBoot.main.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.main.dao.admin.UserMapper;
import com.SpringBoot.main.pojo.admin.User;
import com.SpringBoot.main.service.admin.UserService;

@Service
public class UserServiceImpl implements UserService{
	 	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userMapper.findUserByUsername(username);
	}

	@Override
	public List<User> findList(Map<String, Object> query) {
		// TODO Auto-generated method stub
		return userMapper.findList(query);
	}

	@Override
	public int add(User user) {
		// TODO Auto-generated method stub
		return userMapper.add(user);
	}

	@Override
	public int edit(User user) {
		// TODO Auto-generated method stub
		return userMapper.edit(user);
	}

	@Override
	public int editPassword(User user) {
		// TODO Auto-generated method stub
		return userMapper.editPassword(user);
	}

	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		return userMapper.delete(ids);
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return userMapper.getTotal(queryMap);
	}


	
	
	
}
