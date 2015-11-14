package com.choice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.choice.idao.rzrqMapper;
import com.choice.model.Rzrq;
import com.choice.model.RzrqKey;
import com.choice.service.IRZRQservice;
import com.choice.utils.WebRequestUtil;

@Service
public class RZRQserviceImpl implements IRZRQservice {

	private static Logger log = LoggerFactory.getLogger(RZRQserviceImpl.class);
	@Resource
	private rzrqMapper rzrqDao;

	public List<Rzrq> getRZRQfromWeb() {
		WebRequestUtil webRequestUtil = new WebRequestUtil();
		String webString = webRequestUtil.getRequest();
		List<Rzrq> rzrqList = webRequestUtil.formatRZRQ(webString);
		log.info(ToStringBuilder.reflectionToString(rzrqList));
		return rzrqList;
	}

	public int deleteByPrimaryKey(RzrqKey key) {
		return rzrqDao.deleteByPrimaryKey(key);
	}

	public int insert(Rzrq record) {
		return rzrqDao.insert(record);
	}

	public int insertSelective(Rzrq record) {
		return rzrqDao.insertSelective(record);
	}

	public Rzrq selectByPrimaryKey(RzrqKey key) {
		return rzrqDao.selectByPrimaryKey(key);
	}

	public int updateByPrimaryKeySelective(Rzrq record) {
		return rzrqDao.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(Rzrq record) {
		return rzrqDao.updateByPrimaryKey(record);
	}

}
