package com.choice.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.choice.idao.rzrqMapper;
import com.choice.model.Rzrq;
import com.choice.model.RzrqKey;
import com.choice.model.RzrqRequestHand;
import com.choice.service.IRZRQservice;
import com.choice.service.IRzrqRequestHandService;
import com.choice.utils.WebRequestUtil;

@Service("RZRQserviceImpl")
public class RZRQserviceImpl implements IRZRQservice {

	private static Logger log = LoggerFactory.getLogger(RZRQserviceImpl.class);
	@Resource
	private rzrqMapper rzrqDao;
	@Resource(name="RzrqRequestHandServiceImpl")
	private IRzrqRequestHandService rzrqRequestHandService;
	@Resource
	private WebRequestUtil webRequestUtil;
	
	public List<Rzrq> getRZRQfromWeb() {
		RzrqRequestHand rzrqRequestHand = rzrqRequestHandService.selectByPrimaryKey(1);
		Date dbEndDay = rzrqRequestHand.getEnddate();
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dbEndDay);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		String webString = webRequestUtil.getRequest(new Date(calendar.getTimeInMillis()), today);
		List<Rzrq> rzrqList = webRequestUtil.formatRZRQ(webString);
		log.info(ToStringBuilder.reflectionToString(rzrqList));
		return rzrqList;
	}

	public int deleteByPrimaryKey(RzrqKey key) {
		return rzrqDao.deleteByPrimaryKey(key);
	}

	public int insert(Rzrq record) {
		int result = rzrqDao.insert(record);
		if(result > 0){
			RzrqRequestHand rzrqRequestHand = rzrqRequestHandService.selectByPrimaryKey(1);
			Date dbEndDay = rzrqRequestHand.getEnddate();
			if(record.getOpdate().after(dbEndDay)){
				rzrqRequestHand.setEnddate(record.getOpdate());
			}
			log.debug("insert RZRQ data success: "+ToStringBuilder.reflectionToString(record));
		}else{
			log.error("insert RZRQ data not success: "+ToStringBuilder.reflectionToString(record));
		}
		return result;
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

	public List<Rzrq> selectRecent(RzrqKey key) {
		return rzrqDao.selectRecent(key);
	}

}
