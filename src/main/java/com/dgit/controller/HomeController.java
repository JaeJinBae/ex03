package com.dgit.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="/doA",method=RequestMethod.GET)
	public String doAGet(Model model){
		logger.info("[doAAAAAA] GET =======================================");
		return "doA";
	}
	
	@RequestMapping(value="/doB",method=RequestMethod.GET)
	public String doBGet(Model model){
		logger.info("[doBBBBBB] GET =======================================");
		model.addAttribute("result","doB의 result!");
		return "doB";
	}
	
	@RequestMapping(value="/test1",method=RequestMethod.GET)
	public String test1(Model model){
		logger.info("[test1 ~~~~~] GET =======================================");
		model.addAttribute("result","test1의 result!");
		return "test1";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginGet(Model model){
		logger.info("[login ~~~~~] GET =======================================");
		model.addAttribute("result","doB의 result!");
		return "login";
	}
}
