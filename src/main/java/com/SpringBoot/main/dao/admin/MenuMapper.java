package com.SpringBoot.main.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.SpringBoot.main.pojo.admin.Menu;

@Repository
public interface MenuMapper {
		
	public int add(Menu menu);
	public List<Menu> findMenusByPage(Map<String, Object> query);
	public int findTotalByName(Map<String, Object> query);
	public List<Menu> findTopList();
	public int edit(Menu menu);
	public int delete(Integer id);
	public List<Menu> findLowerList(Integer id);
	public List<Menu> findMenuByIds(String menuIds);
}
