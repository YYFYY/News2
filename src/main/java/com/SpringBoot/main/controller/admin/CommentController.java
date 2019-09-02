package com.SpringBoot.main.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SpringBoot.main.page.admin.Page;
import com.SpringBoot.main.pojo.admin.Comment;
import com.SpringBoot.main.service.admin.CommentService;
import com.SpringBoot.main.service.admin.NewsCategoryService;
import com.SpringBoot.main.service.admin.NewsService;
import com.github.pagehelper.util.StringUtil;

@Controller
@RequestMapping("admin/comment")
public class CommentController {

	@Autowired
	private NewsCategoryService newsCategoryService;

	@Autowired
	private NewsService newsService;

	@Autowired
	private CommentService commentService;

	@RequestMapping("list")
	public String list(Model model) {
		model.addAttribute("newsCategoryList", newsCategoryService.findAll());
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("offset", 0);
		query.put("pageSize", 999);
		model.addAttribute("newList", newsService.findList(query));
		return "comment/list";
	}

	@RequestMapping("list2")
	@ResponseBody
	public Map<String, Object> list2(
			@RequestParam(name = "nickname", defaultValue = "", required = false) String nickname,
			@RequestParam(name = "content", defaultValue = "", required = false) String content,
			@RequestParam(name = "text", defaultValue = "", required = false) String text,Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("nickname", nickname);
		query.put("content", content);

		query.put("offset", page.getOffset());
		query.put("pageSize", page.getRows());

		List<Comment> list = commentService.findList(query);
		List<Comment> list2= new ArrayList<Comment>();
		if(StringUtil.isEmpty(text)) {
			list2=list;
		}
		else {
			for (Comment comment : list) {
				if(comment.getNews().getTitle().contains(text))
					list2.add(comment);
			}
		}
		
		
		map.put("rows", list2);
		map.put("total", commentService.getTotal(query));
		return map;
	}

	@RequestMapping("add")
	@ResponseBody
	public Map<String, Object> add(Comment comment) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (comment == null) {
			map.put("type", "error");
			map.put("msg", "请检查输入的评论信息！");
			return map;
		}
		if (comment.getNewsId() == null) {
			map.put("type", "error");
			map.put("msg", "请选择评论文章！");
			return map;
		}
		if (StringUtil.isEmpty(comment.getNickname())) {
			map.put("type", "error");
			map.put("msg", "请检查评论昵称！");
			return map;
		}
		if (StringUtil.isEmpty(comment.getContent())) {
			map.put("type", "error");
			map.put("msg", "请填写评论内容！");
			return map;
		}

		comment.setCreateTime(new Date());

		if (commentService.add(comment) <= 0) {
			map.put("type", "error");
			map.put("msg", "添加失败，请联系管理员！");
			return map;
		}

		newsService.updateCommentNumber(comment.getNewsId());
		map.put("type", "success");
		map.put("msg", "添加成功！");

		return map;
	}

	@RequestMapping("edit")
	@ResponseBody
	public Map<String, Object> edit(Comment comment) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (comment == null) {
			map.put("type", "error");
			map.put("msg", "请检查输入的评论信息！");
			return map;
		}
		if (comment.getNewsId() == null) {
			map.put("type", "error");
			map.put("msg", "请选择评论文章！");
			return map;
		}
		if (StringUtil.isEmpty(comment.getNickname())) {
			map.put("type", "error");
			map.put("msg", "请检查评论昵称！");
			return map;
		}
		if (StringUtil.isEmpty(comment.getContent())) {
			map.put("type", "error");
			map.put("msg", "请填写评论内容！");
			return map;
		}
		if (commentService.edit(comment) <= 0) {
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
	public Map<String, Object> delete(String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (ids == null) {
			map.put("msg", "请选择要删除的数据！");
			map.put("type", "error");
			return map;
		}
	    if(ids.contains(",")) {
		ids=ids.substring(0,ids.length()-1);
	}

		if (commentService.delete(ids) <= 0) {
			map.put("type", "error");
			map.put("msg", "删除失败，请联系管理员！");
			return map;
		}

		map.put("type", "success");
		map.put("msg", "删除成功！");

		return map;
	}
}
