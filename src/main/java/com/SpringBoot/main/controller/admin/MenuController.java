package com.SpringBoot.main.controller.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SpringBoot.main.page.admin.Page;
import com.SpringBoot.main.pojo.admin.Menu;
import com.SpringBoot.main.service.admin.MenuService;
import com.github.pagehelper.util.StringUtil;

@Controller
@RequestMapping("admin/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@RequestMapping("list")
	public String menu(Model model) {
		model.addAttribute("topList", menuService.findTopList());
		return "menu/list";
	}

	@RequestMapping("list2")
	@ResponseBody
	public Map<String, Object> list2(@RequestParam(value = "name", required = false, defaultValue = "") String name,
			Page page) {
		Map<String, Object> rs = new HashMap<String, Object>();
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("offset", page.getOffset());
		query.put("name", name);
		query.put("pageSize", page.getRows());
		List<Menu> list = menuService.findMenusByPage(query);
		rs.put("rows", list);
		rs.put("total", menuService.findTotalByName(query));
		return rs;
	}

	@RequestMapping("getIcons")
	@ResponseBody
	public Map<String, Object> getIcons(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		File file = new File(request.getServletContext().getRealPath("/") + "\\resources\\admin\\easyui\\css\\icons");
		if (!file.exists()) {
			map.put("type", "error");
			map.put("msg", "文件目录不存在");
		}
		File[] listFiles = file.listFiles();
		List<String> icons = new ArrayList<String>();
		for (File file2 : listFiles) {
			if (file2 != null && file2.getName().contains("png"))
				icons.add("icon-" + file2.getName().substring(0, file2.getName().indexOf(".")).replace("_", "-"));
		}
		map.put("type", "success");
		map.put("content", icons);

		return map;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Menu menu) {

		Map<String, String> map = new HashMap<String, String>();
		if (menu == null) {
			map.put("msg", "请填写正确的信息！");
			map.put("type", "error");
			return map;
		}
		if (StringUtil.isEmpty(menu.getName())) {
			map.put("msg", "请填写菜单名称！");
			map.put("type", "error");
			return map;
		}
		if (StringUtil.isEmpty(menu.getIcon())) {
			map.put("msg", "请上传菜单图标！");
			map.put("type", "error");
			return map;
		}
		if (menu.getParentId() == null) {
			menu.setParentId(0l);
		}
		if (menuService.add(menu) <= 0) {
			map.put("msg", "添加失败！");
			map.put("type", "error");
			return map;
		}

		map.put("msg", "添加成功！");
		map.put("type", "success");

		return map;
	}
	
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Menu menu) {

		Map<String, String> map = new HashMap<String, String>();
		if (menu == null) {
			map.put("msg", "请填写正确的信息！");
			map.put("type", "error");
			return map;
		}
		if (StringUtil.isEmpty(menu.getName())) {
			map.put("msg", "请填写菜单名称！");
			map.put("type", "error");
			return map;
		}
		if (StringUtil.isEmpty(menu.getIcon())) {
			map.put("msg", "请上传菜单图标！");
			map.put("type", "error");
			return map;
		}
		if (menu.getParentId() == null) {
			menu.setParentId(0l);
		}
		if (menuService.edit(menu) <= 0) {
			map.put("msg", "修改失败！");
			map.put("type", "error");
			return map;
		}

		map.put("msg", "修改成功！");
		map.put("type", "success");

		return map;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestParam( name="id",required = true)Integer id ){
		Map<String, Object> map = new HashMap<String, Object>();
		if(id==null) {
			map.put("msg", "请选择一条记录信息！");
			map.put("type", "error");
			return map;
		}
		if(menuService.findLowerList(id)!=null && menuService.findLowerList(id).size()>0) {
			map.put("msg", "请先删除低级菜单");
			map.put("type", "error");
			return map;
		}
		if (menuService.delete(id) <= 0) {
			map.put("msg", "删除失败！请联系管理员");
			map.put("type", "error");
			return map;
		}
		
		map.put("msg", "删除成功！");
		map.put("type", "success");
		
		return map;
	}
}
