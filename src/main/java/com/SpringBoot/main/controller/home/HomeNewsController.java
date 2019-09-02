package com.SpringBoot.main.controller.home;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SpringBoot.main.page.admin.Page;
import com.SpringBoot.main.pojo.admin.Comment;
import com.SpringBoot.main.pojo.admin.News;
import com.SpringBoot.main.service.admin.CommentService;
import com.SpringBoot.main.service.admin.NewsCategoryService;
import com.SpringBoot.main.service.admin.NewsService;
import com.github.pagehelper.util.StringUtil;

@Controller
@RequestMapping("news")
public class HomeNewsController {
	@Autowired
	private NewsCategoryService newsCategoryService;

	@Autowired
	private NewsService newsService;
	
	@Autowired
	private CommentService commentService;

	@RequestMapping("detail")
	public String detail(Model model, Long id) {
		News news = newsService.find(id);
		model.addAttribute("tags",news.getTags().split(","));
		model.addAttribute("title",news.getTitle());
		model.addAttribute("news",news);
		model.addAttribute("newsCategoryList", newsCategoryService.findAll());
		newsService.updateViewNumber(id);
		return "home/news/detail";
	}

	@RequestMapping("category_list")
	public String categoryList(Model model, @RequestParam(value = "cid", required = true) Long cid, Page page) {
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("offset", 0);
		query.put("pageSize", 10);
		query.put("categoryId", cid);
		model.addAttribute("newsCategory", newsCategoryService.find(cid));
		model.addAttribute("newsList", newsService.findList(query));
		model.addAttribute("newsCategoryList", newsCategoryService.findAll());
		return "home/news/category_list";
	}

	@RequestMapping("last_comment_list")
	@ResponseBody
	public Map<String, Object> lastCommentList(Model model) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("type", "success");
		map.put("newsList", newsService.findLastCommentList(10));
		model.addAttribute("newsList", newsService.findLastCommentList(10));
		model.addAttribute("newsCategoryList", newsCategoryService.findAll());
		return map;
	}

	@RequestMapping("get_category_list")
	@ResponseBody
	public Map<String, Object> getCategoryList(Page page,
			@RequestParam(value="cid",required = true)Long cid) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("offset", page.getOffset());
		query.put("pageSize", page.getRows());
		query.put("categoryId", cid);
		
		map.put("type","success");
		map.put("newsList", newsService.findList(query));
		return map;
	}
	
	@RequestMapping("search_list")
	public String searchList(Model model, @RequestParam(value = "keyWord", required = true) String keyWord) {
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("offset", 0);
		query.put("pageSize", 10);
		query.put("name", keyWord);
		model.addAttribute("keyWord", keyWord);
		model.addAttribute("newsList", newsService.findList(query));
		model.addAttribute("newsCategoryList", newsCategoryService.findAll());
		return "home/news/search_list";
	}
	
	@RequestMapping("get_search_list")
	@ResponseBody
	public Map<String, Object> getSearchList(Page page,
			@RequestParam(value = "keyWord", required = true) String keyWord) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("offset", page.getOffset());
		query.put("pageSize", page.getRows());
		query.put("name", keyWord);
		
		map.put("type","success");
		map.put("newsList", newsService.findList(query));
		return map;
	}
	
	@RequestMapping("comment_news")
	@ResponseBody
	public Map<String, Object> commentNews(Comment comment) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(comment==null) {
			map.put("type", "error");
			map.put("msg", "请检查输入的数据");
			return map;
		}
		if(StringUtil.isEmpty(comment.getNickname())) {
			map.put("type", "error");
			map.put("msg", "请输入昵称");
			return map;
		}
		if(StringUtil.isEmpty(comment.getContent())) {
			map.put("type", "error");
			map.put("msg", "请输入评论内容");
			return map;
		}
		comment.setCreateTime(new Date());
		
		if(commentService.add(comment)<=0) {
			map.put("type", "error");
			map.put("msg", "评论失败，请联系管理员");
			return map;
		}
		
		newsService.updateCommentNumber(comment.getNewsId());
		
		map.put("createTime", comment.getCreateTime());
		map.put("type","success");
		map.put("msg", "评论成功");
		return map;
	}
	
	
	@RequestMapping("get_comment_list")
	@ResponseBody
	public Map<String, Object> getCommentList(Page page,
			@RequestParam(value = "newsId", required = true) Long newsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("offset", page.getOffset());
		query.put("pageSize", page.getRows());
		query.put("newsId", newsId);
		
		map.put("type","success");
		map.put("commentList", commentService.findList(query));
		return map;
	}
	
}
