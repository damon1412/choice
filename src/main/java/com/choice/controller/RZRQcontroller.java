package com.choice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choice.model.RZRQ;
import com.choice.service.RZQRQservice;
@Controller
@RequestMapping(value="/qzrq",method=RequestMethod.GET)
public class RZRQcontroller {
	private static Logger log = LoggerFactory.getLogger(RZRQcontroller.class);
	@Autowired
	private RZQRQservice RZRQservice;
	@RequestMapping(value="/getRZRQfromWeb",method=RequestMethod.GET)
	public @ResponseBody List<RZRQ> getRZRQinJson(){
		List<RZRQ> rzrq = RZRQservice.getRZRQfromWeb();
		return rzrq;
	}
	
}
