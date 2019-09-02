package com.SpringBoot.main.service.admin;

import java.util.List;
import java.util.Map;

import com.SpringBoot.main.pojo.admin.User;

public interface UserService {
	public User findUserByUsername(String username);

	public List<User> findList(Map<String, Object> query);
	
	public int add(User user);
	public int edit(User user);
	public int editPassword(User user);
	public int delete(String ids);
	public int getTotal(Map<String, Object> queryMap);
}
