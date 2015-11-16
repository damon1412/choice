package com.choice.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choice.model.Rzrq;
import com.choice.model.RzrqKey;
import com.choice.service.IRZRQservice;
@Controller
@RequestMapping(value="/rzrq",method=RequestMethod.GET)
public class RZRQcontroller {
	private static Logger log = LoggerFactory.getLogger(RZRQcontroller.class);
	@Resource(name="RZRQserviceImpl")
	private IRZRQservice rzrqService;
	/**
	 * 获取web网站融资融券数据
	 * @return
	 */
	@RequestMapping(value="/getRZRQfromWeb",method=RequestMethod.GET)
	public @ResponseBody List<Rzrq> getRZRQinJson(){
		List<Rzrq> rzrq = rzrqService.getRZRQfromWeb();
		return rzrq;
	}
	/**
	 * 把融资融券数据更新到最新
	 * @return
	 */
    @RequestMapping("/update")
    public String updateRZRQtoNew() {
		List<Rzrq> rzrqList = rzrqService.getRZRQfromWeb();
		for(Rzrq rzrq : rzrqList){
			int result = rzrqService.insert(rzrq);
			log.info(result+" - "+ToStringBuilder.reflectionToString(rzrq));
		}
        return "success";
    }
    
    /**
     * 从数据库获取rzrq数据
     */
    @RequestMapping(value="/selectRecent",method=RequestMethod.GET)
    public @ResponseBody List<Rzrq> getRzrqDateFromDB() {
    	RzrqKey key = new RzrqKey();
    	key.setStockcode("1");
    	return rzrqService.selectRecent(key);
    }
}
