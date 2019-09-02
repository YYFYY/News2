package com.SpringBoot.main.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.SpringBoot.main.page.admin.Page;
import com.SpringBoot.main.pojo.admin.Log;
import com.SpringBoot.main.service.admin.LogService;
import com.SpringBoot.main.service.admin.RoleService;
import com.SpringBoot.main.service.admin.UserService;

@Controller
@RequestMapping("admin/log")
public class LogController {
	
	@Autowired
	private LogService logService;
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("list")
	public ModelAndView list(ModelAndView model){
		Map<String, Object> query=new HashMap<String, Object>();
		
		model.setViewName("log/list");
		
		return model;
	}
	
	@RequestMapping(value = "/list2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(Page page,
			@RequestParam(name = "content", required = false, defaultValue = "") String content
			) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("content", content);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", logService.findList(queryMap));
		ret.put("total", logService.getTotal(queryMap));
		return ret;
	}
	
	@RequestMapping("add")
	@ResponseBody
	public Map<String, String> add(Log log) {
		Map<String, String> map = new HashMap<String, String>();
		if (log == null) {
			map.put("msg", "请输入相关信息！");
			map.put("type", "error");
			return map;
		}
		if (log.getContent().isEmpty()) {
			map.put("msg", "请输入内容！");
			map.put("type", "error");
			return map;
		}

		log.setCreateTime(new Date());
		if (logService.add(log) <= 0) {
			map.put("msg", "添加失败，请联系管理员！");
			map.put("type", "error");
			return map;
		}
		map.put("msg", "添加成功！");
		map.put("type", "success");
		return map;
	}

	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, String> delete(String ids) {
		Map<String, String> map = new HashMap<String, String>();
		if (ids == null) {
			map.put("msg", "请选择要删除的数据！");
			map.put("type", "error");
			return map;
		}
	if(ids.contains(",")) {
		ids=ids.substring(0,ids.length()-1);
	}
		
		if (logService.delete(ids) <= 0) {
			map.put("msg", "删除失败，请联系管理员！");
			map.put("type", "error");
			return map;
		}
		map.put("msg", "修改成功！");
		map.put("type", "success");
		return map;
	}
	

}
