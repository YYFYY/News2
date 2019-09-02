package com.SpringBoot.main.service.admin;

import java.util.List;
import java.util.Map;

import com.SpringBoot.main.pojo.admin.Menu;

public interface MenuService {
		public int add(Menu menu);
		public List<Menu> findMenusByPage(Map<String, Object> query);
		public int findTotalByName(Map<String, Object> query);
		public List<Menu> findTopList();
		public int edit(Menu menu);
		public int delete(Integer id);
		public List<Menu> findLowerList(Integer id);
		public List<Menu> findMenuByIds(String menuIds);
}
