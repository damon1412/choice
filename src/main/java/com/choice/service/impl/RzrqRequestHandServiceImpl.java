package com.choice.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.choice.idao.RzrqRequestHandMapper;
import com.choice.model.RzrqRequestHand;
import com.choice.service.IRzrqRequestHandService;
@Service("RzrqRequestHandServiceImpl")
public class RzrqRequestHandServiceImpl implements IRzrqRequestHandService {
	@Resource
	private RzrqRequestHandMapper rzrqRequestHandMapperDao;

	public int deleteByPrimaryKey(Integer requestId) {
		return rzrqRequestHandMapperDao.deleteByPrimaryKey(requestId);
	}

	public int insert(RzrqRequestHand record) {
		return rzrqRequestHandMapperDao.insert(record);
	}

	public int insertSelective(RzrqRequestHand record) {
		return rzrqRequestHandMapperDao.insertSelective(record);
	}

	public RzrqRequestHand selectByPrimaryKey(Integer requestId) {
		return rzrqRequestHandMapperDao.selectByPrimaryKey(requestId);
	}

	public int updateByPrimaryKeySelective(RzrqRequestHand record) {
		return rzrqRequestHandMapperDao.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(RzrqRequestHand record) {
		return rzrqRequestHandMapperDao.updateByPrimaryKey(record);
	}

}
