package com.SpringBoot.main.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.SpringBoot.main.pojo.admin.Authority;

@Repository
public interface AuthorityMapper {
	public int add(Authority authority);
	public int deleteByRoleId(Long roleId);
	public List<Authority> findListByRoleId(Map<String, Object> query);
	
}
