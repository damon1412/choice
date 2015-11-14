package com.choice.controller;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choice.model.Rzrq;
import com.choice.service.IRZRQservice;
@Controller
@RequestMapping(value="/rzrq",method=RequestMethod.GET)
public class RZRQcontroller {
	private static Logger log = LoggerFactory.getLogger(RZRQcontroller.class);
	@Autowired
	private IRZRQservice rzrqService;
	
	@RequestMapping(value="/getRZRQfromWeb",method=RequestMethod.GET)
	public @ResponseBody List<Rzrq> getRZRQinJson(){
		List<Rzrq> rzrq = rzrqService.getRZRQfromWeb();
		return rzrq;
	}
	
    @RequestMapping("/insert")
    public String requestTest5() {
		List<Rzrq> rzrqList = rzrqService.getRZRQfromWeb();
		for(Rzrq rzrq : rzrqList){
			int result = rzrqService.insert(rzrq);
			log.info(result+" - "+ToStringBuilder.reflectionToString(rzrq));
		}
        return "success";
    }
}
