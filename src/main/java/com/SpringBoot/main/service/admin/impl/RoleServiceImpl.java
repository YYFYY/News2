package com.SpringBoot.main.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.main.dao.admin.RoleMapper;
import com.SpringBoot.main.pojo.admin.Role;
import com.SpringBoot.main.service.admin.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleMapper roleMapper;
	
	
	@Override
	public int add(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.add(role);
	}

	@Override
	public int edit(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.edit(role);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return roleMapper.delete(id);
	}

	@Override
	public List<Role> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return roleMapper.findList(queryMap);
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return roleMapper.getTotal(queryMap);
	}

	@Override
	public Role find(Long id) {
		// TODO Auto-generated method stub
		return roleMapper.find(id);
	}

}
