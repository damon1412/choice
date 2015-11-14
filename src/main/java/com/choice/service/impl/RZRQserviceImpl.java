package com.choice.service.impl;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.choice.model.RZRQ;
import com.choice.service.RZQRQservice;
import com.choice.utils.WebRequestUtil;

public class RZRQserviceImpl extends RZQRQservice {
	public RZRQserviceImpl() {
		super();
	}

	private static Logger log = LoggerFactory.getLogger(RZRQserviceImpl.class);

	@Override
	public List<RZRQ> getRZRQfromWeb() {
		WebRequestUtil webRequestUtil = new WebRequestUtil();
		String webString = webRequestUtil.getRequest();
		List<RZRQ> rzrqList = webRequestUtil.formatRZRQ(webString);
		log.info(ToStringBuilder.reflectionToString(rzrqList));
		return rzrqList;
	}

	@Override
	public void saveRzrq(List<RZRQ> rzrqList) {
		// TODO Auto-generated method stub

	}

}
