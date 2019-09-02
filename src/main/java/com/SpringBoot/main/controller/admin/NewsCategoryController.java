package com.SpringBoot.main.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SpringBoot.main.page.admin.Page;
import com.SpringBoot.main.pojo.admin.NewsCategory;
import com.SpringBoot.main.service.admin.NewsCategoryService;
import com.github.pagehelper.util.StringUtil;

@Controller
@RequestMapping("admin/news_category")
public class NewsCategoryController {
	
	
	@Autowired
	private NewsCategoryService newsCategoryService;
	
	@RequestMapping("list")
	public String list() {
		
		return "news_category/list";
	}
	
	
	@RequestMapping("list2")
	@ResponseBody
	public Map<String,Object> list2(@RequestParam(name="name",defaultValue = "",required = false) String name,
			Page page){
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> query = new HashMap<String, Object>();
		query.put("name", name);
		query.put("offset", page.getOffset());
		query.put("pageSize", page.getRows());
		
		
		
		map.put("rows", newsCategoryService.findList(query));
		map.put("total", newsCategoryService.getTotal(query));
		return map;
	}
	
	@RequestMapping("add")
	@ResponseBody
	public Map<String,Object> add(NewsCategory newsCategory){
		Map<String,Object> map = new HashMap<String, Object>();
		if(newsCategory==null) {
			map.put("type", "error");
			map.put("msg", "请检查输入的分类信息！");
			return map;
		}
		if(StringUtil.isEmpty(newsCategory.getName())) {
			map.put("type", "error");
			map.put("msg", "请检查输入的分类名！");
			return map;
		}
		if(newsCategoryService.add(newsCategory)<=0) {
			map.put("type", "error");
			map.put("msg", "添加失败，请联系管理员！");
			return map;
		}
		map.put("type", "success");
		map.put("msg", "添加成功！");
		
		return map;
	}
	
	@RequestMapping("edit")
	@ResponseBody
	public Map<String,Object> edit(NewsCategory newsCategory){
		Map<String,Object> map = new HashMap<String, Object>();
		if(newsCategory==null) {
			map.put("type", "error");
			map.put("msg", "请检查输入的分类信息！");
			return map;
		}
		if(StringUtil.isEmpty(newsCategory.getName())) {
			map.put("type", "error");
			map.put("msg", "请检查输入的分类名！");
			return map;
		}
		if(newsCategoryService.edit(newsCategory)<=0) {
			map.put("type", "error");
			map.put("msg", "修改失败，请联系管理员！");
			return map;
		}
		map.put("type", "success");
		map.put("msg", "修改成功！");
		
		return map;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(Long id){
		Map<String,Object> map = new HashMap<String, Object>();
		if(id==null) {
			map.put("type", "error");
			map.put("msg", "请选择一条记录！");
			return map;
		}
	try {
		if(newsCategoryService.delete(id)<=0) {
			map.put("type", "error");
			map.put("msg", "删除失败，请联系管理员！");
			return map;
		}
	} catch (Exception e) {
		map.put("type", "error");
		map.put("msg", "删除失败，请联系管理员！");
		return map;
	}
	
		map.put("type", "success");
		map.put("msg", "删除成功！");
		
		return map;
	}
	
}
