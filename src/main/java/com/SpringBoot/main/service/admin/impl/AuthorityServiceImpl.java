package com.SpringBoot.main.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.main.dao.admin.AuthorityMapper;
import com.SpringBoot.main.pojo.admin.Authority;
import com.SpringBoot.main.service.admin.AuthorityService;


@Service
public class AuthorityServiceImpl  implements AuthorityService{
	
	@Autowired
	private AuthorityMapper authorityMapper;

	@Override
	public int add(Authority authority) {
		// TODO Auto-generated method stub
		return authorityMapper.add(authority);
	}

	@Override
	public int deleteByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return authorityMapper.deleteByRoleId(roleId);
	}

	@Override
	public List<Authority> findListByRoleId(Map<String, Object> query) {
		// TODO Auto-generated method stub
		return authorityMapper.findListByRoleId(query);
	}
	
	
}
