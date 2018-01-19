package com.java.collection.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/web")
public class TestController2 {
	private static final Logger log = Logger.getLogger(TestController2.class);
 
	@RequestMapping(value = "/api1")
	public String getValue(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
		log.info("...................incoming.......................");
		return "index";  
	}
	
}
