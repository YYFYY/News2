package com.SpringBoot.main.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.main.dao.admin.MenuMapper;
import com.SpringBoot.main.pojo.admin.Menu;
import com.SpringBoot.main.service.admin.MenuService;


@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	public int add(Menu menu) {
		// TODO Auto-generated method stub
		return menuMapper.add(menu);
	}

	@Override
	public List<Menu> findMenusByPage(Map<String, Object> query) {
		// TODO Auto-generated method stub
		return menuMapper.findMenusByPage(query);
	}

	@Override
	public int findTotalByName(Map<String, Object> query) {
		// TODO Auto-generated method stub
		return menuMapper.findTotalByName(query);
	}

	@Override
	public List<Menu> findTopList() {
		// TODO Auto-generated method stub
		return menuMapper.findTopList();
	}

	@Override
	public int edit(Menu menu) {
		// TODO Auto-generated method stub
		return menuMapper.edit(menu);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return menuMapper.delete(id);
	}

	@Override
	public List<Menu> findLowerList(Integer id) {
		// TODO Auto-generated method stub
		return menuMapper.findLowerList(id);
	}

	@Override
	public List<Menu> findMenuByIds(String menuIds) {
		// TODO Auto-generated method stub
		return menuMapper.findMenuByIds(menuIds);
	}
		 
}
