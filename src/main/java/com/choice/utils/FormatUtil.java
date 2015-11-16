package com.choice.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormatUtil {
	private static final Logger log = LoggerFactory.getLogger(FormatUtil.class);
	public static String formatDateToString(Date day){
		log.debug(day.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDay = sdf.format(day);
		strDay = strDay.replaceAll("-", "");
		log.debug(strDay);
		return strDay;
	}
}
