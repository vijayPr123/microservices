package com.eureka.zuul.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class NavigationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NavigationController.class);
	
	@GetMapping("/")
    public String displayBase() {
        return "login";
    }
	
	@GetMapping("/login")
    public String displayLogin() {
        return "login";
    }
	
	@GetMapping("/home")
    public String displayHome() {
        return "home";
    }

}
