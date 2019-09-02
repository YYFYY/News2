package com.SpringBoot.main.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SpringBoot.main.page.admin.Page;
import com.SpringBoot.main.pojo.admin.Authority;
import com.SpringBoot.main.pojo.admin.Menu;
import com.SpringBoot.main.pojo.admin.Role;
import com.SpringBoot.main.service.admin.AuthorityService;
import com.SpringBoot.main.service.admin.MenuService;
import com.SpringBoot.main.service.admin.RoleService;

@Controller
@RequestMapping("admin/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private AuthorityService authorityService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "role/list";
	}

	@RequestMapping(value = "/list2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(Page page,
			@RequestParam(name = "name", required = false, defaultValue = "") String name) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", roleService.findList(queryMap));
		ret.put("total", roleService.getTotal(queryMap));
		return ret;
	}

	@RequestMapping("add")
	@ResponseBody
	public Map<String, String> add(Role role) {
		Map<String, String> map = new HashMap<String, String>();
		if (role == null) {
			map.put("msg", "请输入相关信息！");
			map.put("type", "error");
			return map;
		}
		if (role.getName().isEmpty()) {
			map.put("msg", "请输入角色名称！");
			map.put("type", "error");
			return map;
		}
		if (roleService.add(role) <= 0) {
			map.put("msg", "添加失败，请联系管理员！");
			map.put("type", "error");
			return map;
		}
		map.put("msg", "添加成功！");
		map.put("type", "success");
		return map;
	}

	@RequestMapping("edit")
	@ResponseBody
	public Map<String, String> edit(Role role) {
		Map<String, String> map = new HashMap<String, String>();
		if (role == null) {
			map.put("msg", "请输入相关信息！");
			map.put("type", "error");
			return map;
		}
		if (role.getName().isEmpty()) {
			map.put("msg", "请输入角色名称！");
			map.put("type", "error");
			return map;
		}
		if (roleService.edit(role) <= 0) {
			map.put("msg", "修改失败，请联系管理员！");
			map.put("type", "error");
			return map;
		}
		map.put("msg", "修改成功！");
		map.put("type", "success");
		return map;
	}

	@RequestMapping("delete")
	@ResponseBody
	public Map<String, String> delete(Long id) {
		Map<String, String> map = new HashMap<String, String>();
		if (id == null) {
			map.put("msg", "请选择一条记录！");
			map.put("type", "error");
			return map;
		}

		try {
			if (roleService.delete(id) <= 0) {
				map.put("msg", "删除失败，请联系管理员！");
				map.put("type", "error");
				return map;
			}
		} catch (Exception e) {
			map.put("msg", "该角色有拥有权限或者用户信息，不能删除！");
			map.put("type", "error");
			return map;
		}
		map.put("msg", "删除成功！");
		map.put("type", "success");

		return map;
	}

	@RequestMapping(value = "get_all_menu", method = RequestMethod.POST)
	@ResponseBody
	public List<Menu> getMenus() {
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("pageSize", 99999);
		query.put("offset", 0);

		return menuService.findMenusByPage(query);
	}

	/**
	 * 获取某个角色的所有权限
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/get_role_authority", method = RequestMethod.POST)
	@ResponseBody
	public List<Authority> getRoleAuthority(@RequestParam(name = "roleId", required = true) Integer roleId) {

		Map<String, Object> query = new HashMap<String, Object>();
		query.put("roleId", roleId);

		return authorityService.findListByRoleId(query);
	}
	
	/**
	 * 
	 * @param ids  拿到的权限信息Id
	 * @param roleId  拿到的角色Id
	 * @return
	 */
	@RequestMapping("add_authority")
	@ResponseBody
	public Map<String,Object> addAuthority(@RequestParam(name = "ids") String ids,
			@RequestParam(name="roleId") Long roleId
			){
		Map<String, Object> map = new HashMap<String, Object>();
		if(ids==null) {
			map.put("msg","请授予相应的权限");
			map.put("type", "error");
			return map;
		}
		
		if(roleId==null) {
			map.put("msg","请选择一个角色");
			map.put("type", "error");
			return map;
		}
		
		//除去最后一个逗号
		if(ids.contains(",")) {
			ids=ids.substring(0,ids.length()-1);
		}
		
		String[] idArr=ids.split(",");
		if(idArr.length>0) {
			authorityService.deleteByRoleId(roleId);
		}
		
		for (String id : idArr) {
			Authority auth=new Authority();
			auth.setMenuId(Long.valueOf(id));
			auth.setRoleId(roleId);
			authorityService.add(auth);
		}
		
		map.put("msg","添加权限成功");
		map.put("type", "success");
		return map;
	}
}
