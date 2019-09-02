package com.SpringBoot.main.service.admin.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.main.dao.admin.LogMapper;
import com.SpringBoot.main.pojo.admin.Log;
import com.SpringBoot.main.service.admin.LogService;

@Service
public class LogServiceImpl implements LogService{

	@Autowired
	private LogMapper logMapper;
	
	@Override
	public int add(Log log) {
		// TODO Auto-generated method stub
		return logMapper.add(log);
	}

	@Override
	public int add(String content) {
		// TODO Auto-generated method stub
		Log log = new Log();
		log.setContent(content);
		log.setCreateTime(new Date());
		return logMapper.add(log);
	}

	@Override
	public List<Log> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return logMapper.findList(queryMap);
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return logMapper.getTotal(queryMap);
	}

	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		return logMapper.delete(ids);
	}

}
