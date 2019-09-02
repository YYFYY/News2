package com.SpringBoot.main.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.main.dao.admin.NewsCategoryMapper;
import com.SpringBoot.main.pojo.admin.NewsCategory;
import com.SpringBoot.main.service.admin.NewsCategoryService;

@Service
public class NewsCategoryServiceImpl  implements NewsCategoryService{
	
	@Autowired
	private NewsCategoryMapper newsCategoryMapper;
	
	
	@Override
	public int add(NewsCategory newsCategory) {
		// TODO Auto-generated method stub
		return newsCategoryMapper.add(newsCategory);
	}

	@Override
	public int edit(NewsCategory newsCategory) {
		// TODO Auto-generated method stub
		return newsCategoryMapper.edit(newsCategory);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return newsCategoryMapper.delete(id);
	}

	@Override
	public List<NewsCategory> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return newsCategoryMapper.findList(queryMap);
	}

	@Override
	public List<NewsCategory> findAll() {
		// TODO Auto-generated method stub
		return newsCategoryMapper.findAll();
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return newsCategoryMapper.getTotal(queryMap);
	}

	@Override
	public NewsCategory find(Long id) {
		// TODO Auto-generated method stub
		return newsCategoryMapper.find(id);
	}

}
