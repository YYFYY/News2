package com.SpringBoot.main.controller.home;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SpringBoot.main.service.admin.NewsCategoryService;
import com.SpringBoot.main.service.admin.NewsService;

@Controller
@RequestMapping("index")
public class IndexController {
	
	@Autowired
	private NewsCategoryService newsCategoryService;
	
	@Autowired
	private NewsService newsService;
	
	@RequestMapping("index") 
	public String index(Model model) {
		Map<String,Object> query = new HashMap<String, Object>();
		query.put("offset", 0);
		query.put("pageSize",10);
		model.addAttribute("newsList",newsService.findList(query));
		model.addAttribute("newsCategoryList",newsCategoryService.findAll());
		return "home/index/index";
	}
	
	@RequestMapping("get_site_info")
	@ResponseBody
	public Map<String,Object> getSiteInfo(){
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> query = new HashMap<String, Object>();
		query.put("offset", 0);
		query.put("pageSize",99999);
		
		map.put("type", "success");
		map.put("totalArticle", newsService.getTotal(query));
		map.put("siteDays", getDayTime("2019-5-25"));
		return map;
	}
	
	public long getDayTime(String data) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = null;
		try {
			startTime = sim.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date endTime = new Date();
		long time = (endTime.getTime()-startTime.getTime())/1000/3600/24;
		return time;
	}
}
