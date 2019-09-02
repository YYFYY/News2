package com.SpringBoot.main.controller.admin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SpringBoot.main.pojo.admin.Authority;
import com.SpringBoot.main.pojo.admin.Menu;
import com.SpringBoot.main.pojo.admin.Role;
import com.SpringBoot.main.pojo.admin.User;
import com.SpringBoot.main.service.admin.AuthorityService;
import com.SpringBoot.main.service.admin.LogService;
import com.SpringBoot.main.service.admin.MenuService;
import com.SpringBoot.main.service.admin.RoleService;
import com.SpringBoot.main.service.admin.UserService;
import com.SpringBoot.main.utils.CpachaUtil;
import com.SpringBoot.main.utils.MenuUtil;
import com.github.pagehelper.util.StringUtil;

@Controller
@RequestMapping("system")
public class SystemController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private LogService logService;

	/**
	 * 系统登录后的主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model, HttpServletRequest request) {
		List<Menu> menus = (List<Menu>) request.getSession().getAttribute("userMenus");
		List<Menu> topMenu = MenuUtil.getTopMenu(menus);
		List<Menu> secondMenu = MenuUtil.getSecondMenu(menus);
		request.getSession().setAttribute("topMenus", topMenu);
		request.getSession().setAttribute("secondMenus", secondMenu);

		model.addAttribute("test", "hello");
		return "system/index";
	}

	@RequestMapping("welcome")
	public String welcome(Model model) {

		return "system/welcome";
	}

	@RequestMapping("login")
	public String login() {
		return "system/login";
	}

	@RequestMapping("cpache")
	public void cpache(@RequestParam(name = "vl", required = false, defaultValue = "4") Integer vcodeLen,
			@RequestParam(name = "w", required = false, defaultValue = "100") Integer width,
			@RequestParam(name = "h", required = false, defaultValue = "30") Integer height,
			@RequestParam(name = "type", required = true, defaultValue = "loginCpacha") String cpacheType,
			HttpServletRequest request, HttpServletResponse reponse) {
		CpachaUtil cpache = new CpachaUtil(vcodeLen, width, height);
		String code = cpache.generatorVCode();
		request.getSession().setAttribute(cpacheType, code);
		BufferedImage generatorRotateVCodeImage = cpache.generatorRotateVCodeImage(code, true);
		try {
			ImageIO.write(generatorRotateVCodeImage, "gif", reponse.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("loginAct")
	@ResponseBody
	public Map<String, String> loginAct(HttpServletRequest request, String cpache, User user) {
		Map<String, String> map = new HashMap<String, String>();

		String loginCpache = (String) request.getSession().getAttribute("loginCpacha");
		if (loginCpache == null || loginCpache.equals("") || !loginCpache.toUpperCase().equals(cpache.toUpperCase())) {
			map.put("msg", "验证码错误");
			map.put("type", "error");
			return map;
		}

		User userByUsername = userService.findUserByUsername(user.getUsername());
		if (userByUsername == null) {
			map.put("msg", "用户名不存在");
			map.put("type", "error");
			return map;
		}
		if (!user.getPassword().equals(userByUsername.getPassword())) {
			map.put("msg", "密码错误");
			map.put("type", "error");
			return map;
		}

		Role role = roleService.find(userByUsername.getRoleId());
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("roleId", role.getId());
		List<Authority> auths = authorityService.findListByRoleId(query);

		String menuIds = "";
		for (Authority authority : auths) {
			menuIds += authority.getMenuId() + ",";
		}
		if (!StringUtil.isEmpty(menuIds)) {
			menuIds = menuIds.substring(0, menuIds.length() - 1);
		}
		List<Menu> menus = menuService.findMenuByIds(menuIds);

		request.getSession().setAttribute("role", role);
		request.getSession().setAttribute("userMenus", menus);
		request.getSession().setAttribute("admin", userByUsername);
		map.put("msg", "登录成功");
		map.put("type", "success");
		logService.add("用户名为" + user.getUsername() + ",角色为" + role.getName() + "的用户登录成功");

		return map;
	}

	@RequestMapping("loginout")
	public String loginOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("role", null);
		session.setAttribute("userMenus", null);
		session.setAttribute("admin", null);

		return "redirect:/system/login";
	}

	@RequestMapping(value = "edit_password", method = RequestMethod.GET)
	public String edit_password() {
		return "system/edit_password";
	}

	@RequestMapping(value = "edit_password2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit_passwordAct(HttpServletRequest request, 
			@RequestParam(name = "newpassword", required = true) String newpassword, 
			@RequestParam(name = "oldpassword", required = true)	String oldpassword) {
		Map<String, String> map = new HashMap<String, String>();
		
		
		if (StringUtil.isEmpty(newpassword)) {
			map.put("msg", "请填写新密码");
			map.put("type", "error");
			return map;
		}

		User user = (User) request.getSession().getAttribute("admin");
		if (!oldpassword.equals(user.getPassword())) {
			map.put("msg", "原密码错误");
			map.put("type", "error");
			return map;
		}
		user.setPassword(newpassword);
		if (userService.editPassword(user) <= 0) {
			map.put("msg", "密码修改失败，请联系管理员");
			map.put("type", "error");
			return map;
		}
		map.put("msg", "密码修改成功");
		map.put("type", "success");
		logService.add("用户名为" + user.getUsername() + "的用户修改密码成功");

		return map;
	}

}
