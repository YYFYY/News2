package com.SpringBoot.main.utils;

import java.util.ArrayList;
import java.util.List;

import com.SpringBoot.main.pojo.admin.Menu;

public class MenuUtil {

	public static List<Menu> getTopMenu(List<Menu> list) {
		List<Menu> menus = new ArrayList<Menu>();
		for (Menu menu : list) {
			if (menu.getParentId() == 0) {
				menus.add(menu);
			}
		}
		return menus;
	}

	public static List<Menu> getSecondMenu(List<Menu> list) {
		List<Menu> menus = new ArrayList<Menu>();
		List<Menu> topMenus = getTopMenu(list);
		for (Menu menu : list) {
			for (Menu topmenu : topMenus) {
				if (menu.getParentId() == topmenu.getId()) {
					menus.add(menu);
					break;
				}
			}
		}

		return menus;
	}

	public static List<Menu> getThirdMenu(List<Menu> list,Long secondMenuId) {
		List<Menu> menus = new ArrayList<Menu>();
		for (Menu menu : list) {
			if (menu.getParentId() == secondMenuId) {
				menus.add(menu);
			}
		}
		return menus;
	}
}
