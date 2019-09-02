package com.SpringBoot.main.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.main.dao.admin.CommentMapper;
import com.SpringBoot.main.pojo.admin.Comment;
import com.SpringBoot.main.service.admin.CommentService;


@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentMapper commentMapper;
	
	
	@Override
	public int add(Comment comment) {
		// TODO Auto-generated method stub
		return commentMapper.add(comment);
	}

	@Override
	public int edit(Comment comment) {
		// TODO Auto-generated method stub
		return commentMapper.edit(comment);
	}

	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		return commentMapper.delete(ids);
	}

	@Override
	public List<Comment> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return commentMapper.findList(queryMap);
	}

	@Override
	public List<Comment> findAll() {
		// TODO Auto-generated method stub
		return commentMapper.findAll();
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return commentMapper.getTotal(queryMap);
	}

}
