package com.SpringBoot.main.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.main.dao.admin.NewsMapper;
import com.SpringBoot.main.pojo.admin.News;
import com.SpringBoot.main.service.admin.NewsService;


@Service
public class NewsServiceImpl implements NewsService{
	
	@Autowired
	private NewsMapper newsMapper;
	
	
	@Override
	public int add(News news) {
		// TODO Auto-generated method stub
		
		int res =  newsMapper.add(news);
		
		return res;
	}

	@Override
	public int edit(News news) {
		// TODO Auto-generated method stub
		return newsMapper.edit(news);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return newsMapper.delete(id);
	}

	@Override
	public List<News> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return newsMapper.findList(queryMap);
	}

	

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return newsMapper.getTotal(queryMap);
	}

	@Override
	public News find(Long id) {
		// TODO Auto-generated method stub
		return newsMapper.find(id);
	}

	@Override
	public int updateCommentNumber(Long id) {
		// TODO Auto-generated method stub
		return newsMapper.updateCommentNumber(id);
	}

	@Override
	public int updateViewNumber(Long id) {
		// TODO Auto-generated method stub
		return newsMapper.updateViewNumber(id);
	}

	@Override
	public List<News> findLastCommentList(int pageSize) {
		// TODO Auto-generated method stub
		return newsMapper.findLastCommentList(pageSize);
	}

}
