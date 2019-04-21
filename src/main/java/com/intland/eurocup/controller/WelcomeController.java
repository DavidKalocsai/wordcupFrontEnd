package com.intland.eurocup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class WelcomeController {
	private static final String WELCOME_PAGE = "welcome";
	
	@RequestMapping("/")
	public String welcome() {
		log.info("Welcome");
		return WELCOME_PAGE;
	}
}