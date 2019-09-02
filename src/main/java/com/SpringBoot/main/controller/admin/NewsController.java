package com.SpringBoot.main.controller.admin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.SpringBoot.main.page.admin.Page;
import com.SpringBoot.main.pojo.admin.News;
import com.SpringBoot.main.service.admin.NewsCategoryService;
import com.SpringBoot.main.service.admin.NewsService;
import com.github.pagehelper.util.StringUtil;

@Controller
@RequestMapping("admin/news")
public class NewsController {
	
	
	@Autowired
	private NewsCategoryService newsCategoryService;
	
	@Autowired
	private NewsService newsService;
	
	@RequestMapping("list")
	public String list(Model model) {
		model.addAttribute("newsCategoryList",newsCategoryService.findAll());
		return "news/list";
	}
	
	@RequestMapping("addlist")
	public String add(Model model) {
		model.addAttribute("newsCategoryList",newsCategoryService.findAll());
		return "news/add";
	}
	
	@RequestMapping("editlist")
	public String edit(Model model,Long id) {
		model.addAttribute("news",newsService.find(id));
		System.out.println(newsService.find(id));
		model.addAttribute("newsCategoryList",newsCategoryService.findAll());
		return "news/edit";
	}
	
	
	@RequestMapping("list2")
	@ResponseBody
	public Map<String,Object> list2(@RequestParam(name="name",defaultValue = "",required = false) String name,
			@RequestParam(name="categoryId",required = false) Long categoryId,
			@RequestParam(name="author",defaultValue = "",required = false) String author,
			Page page){
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> query = new HashMap<String, Object>();
		query.put("name", name);
		query.put("author", author);
		if(categoryId!=null && categoryId.longValue()!=-1) {
			query.put("categoryId", categoryId);
		}
		
		query.put("offset", page.getOffset());
		query.put("pageSize", page.getRows());
		
		
		
		map.put("rows", newsService.findList(query));
		map.put("total", newsService.getTotal(query));
		return map;
	}
	
	@RequestMapping("add")
	@ResponseBody
	public Map<String,Object> add(News news){

		Map<String,Object> map = new HashMap<String, Object>();
		if(news==null) {
			map.put("type", "error");
			map.put("msg", "请检查输入的新闻信息！");
			return map;
		}
		if(StringUtil.isEmpty(news.getTitle())) {
			map.put("type", "error");
			map.put("msg", "请检查新闻标题！");
			return map;
		}
		if(news.getCategoryId()==null) {
			map.put("type", "error");
			map.put("msg", "请选择新闻分类！");
			return map;
		}
		if(news.getAbstrs()==null) {
			map.put("type", "error");
			map.put("msg", "请输入新闻摘要！");
			return map;
		}
		if(news.getTags()==null) {
			map.put("type", "error");
			map.put("msg", "请输入新闻标签！");
			return map;
		}
		if(news.getPhoto()==null) {
			map.put("type", "error");
			map.put("msg", "请选择新闻图片！");
			return map;
		}
		if(StringUtil.isEmpty(news.getAuthor())) {
			map.put("type", "error");
			map.put("msg", "请输入作者！");
			return map;
		}
		if(StringUtil.isEmpty(news.getContent())) {
			map.put("type", "error");
			map.put("msg", "请输入内容！");
			return map;
		}
		
		news.setCreateTime(new Date());
		
		if(newsService.add(news)<=0) {
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
	public Map<String,Object> edit(News news){	
		Map<String,Object> map = new HashMap<String, Object>();
		if(news==null) {
			map.put("type", "error");
			map.put("msg", "请检查输入的新闻信息！");
			return map;
		}
		if(StringUtil.isEmpty(news.getTitle())) {
			map.put("type", "error");
			map.put("msg", "请检查新闻标题！");
			return map;
		}
		if(news.getCategoryId()==null) {
			map.put("type", "error");
			map.put("msg", "请选择新闻分类！");
			return map;
		}
		if(news.getAbstrs()==null) {
			map.put("type", "error");
			map.put("msg", "请输入新闻摘要！");
			return map;
		}
		if(news.getTags()==null) {
			map.put("type", "error");
			map.put("msg", "请输入新闻标签！");
			return map;
		}
		if(news.getPhoto()==null) {
			map.put("type", "error");
			map.put("msg", "请选择新闻图片！");
			return map;
		}
		if(StringUtil.isEmpty(news.getAuthor())) {
			map.put("type", "error");
			map.put("msg", "请输入作者！");
			return map;
		}
		if(StringUtil.isEmpty(news.getContent())) {
			map.put("type", "error");
			map.put("msg", "请输入内容！");
			return map;
		}
		if(newsService.edit(news)<=0) {
			map.put("type", "error");
			map.put("msg", "编辑失败，请联系管理员！");
			return map;
		}
		map.put("type", "success");
		map.put("msg", " 编辑成功！");
		
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
		if(newsService.delete(id)<=0) {
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
	
	
	/**
	 * 图片上传
	 * @param photo
	 * @param request
	 * @return
	 */
	@RequestMapping("upload_photo")
	@ResponseBody
	public Map<String,Object> upload(MultipartFile photo,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		if(photo==null) {
			map.put("msg", "请选择文件上传！");
			map.put("type", "error");
			return map;
		}
		if(photo.getSize()>1024*1024*10)
		{
			map.put("msg", "选择的文件不能超过10M！");
			map.put("type", "error");
			return map;
		}
		//获取文件后缀
		String suffix=photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")+1,photo.getOriginalFilename().length());
		if(!"jpg,jepg,png,gif".toUpperCase().contains(suffix.toUpperCase())) {
			map.put("msg", "请上传后缀为jpg,jepg,png,gif的文件");
			map.put("type", "error");
			return map;
		}
		//保存图片的目录
		//String savePath=request.getServletContext().getRealPath("/")+"/resources/upload/";
		String savePath="C:\\Users\\Administrator\\Pictures\\SpringBoot_News\\";
		
		
		
		
		System.out.println(savePath);
		File file=new File(savePath);
		if(!file.exists()) {
			//如果目录不存在，创建文件
			file.mkdir();
		}
		
		String fileName=UUID.randomUUID()+"."+suffix;
		try {
			photo.transferTo(new File(savePath+fileName));
		} catch (Exception e) {
			map.put("msg", "图片上传失败，请联系管理员");
			map.put("type", "error");
			e.printStackTrace();
			return map;
		} 
		map.put("msg", "图片上传成功");
		map.put("type", "success");
		//map.put("filepath",request.getServletContext().getContextPath() + "/resources/upload/" + fileName );
		map.put("filepath", "http://148.70.102.57/"+fileName);
		return map;
	}
	
}
