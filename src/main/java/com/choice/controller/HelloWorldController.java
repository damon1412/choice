package com.choice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.choice.utils.RequestUtils;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {
	@RequestMapping("/mvc")
    public String helloWorld() {
		System.out.println("---------------HelloWorldController------------------");
		RequestUtils r = new RequestUtils();
		String s = r.getRequest();
		System.out.println(s);
        return "home";
    }
	

}
