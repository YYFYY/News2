package com.SpringBoot.main.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBoot.main.pojo.admin.News;


@Repository
public interface NewsMapper {
	public int add(News news);
	public int edit(News news);
	public int delete(Long id);
	public List<News> findList(Map<String,Object> queryMap);
	
	public int getTotal(Map<String,Object> queryMap);
	public News find(Long id);
	public int updateCommentNumber(Long id);
	public int updateViewNumber(Long id);
	public List<News> findLastCommentList(int pageSize);
	
}
