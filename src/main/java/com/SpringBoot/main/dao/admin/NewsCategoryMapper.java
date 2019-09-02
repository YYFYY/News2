package com.SpringBoot.main.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBoot.main.pojo.admin.NewsCategory;


@Repository
public interface NewsCategoryMapper {
	public int add(NewsCategory newsCategory);
	public int edit(NewsCategory newsCategory);
	public int delete(Long id);
	public List<NewsCategory> findList(Map<String,Object> queryMap);
	public List<NewsCategory> findAll();
	public int getTotal(Map<String,Object> queryMap);
	public NewsCategory find(Long id);
}
