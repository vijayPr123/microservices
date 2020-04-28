package com.spring.mainmodule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NavigationController {

	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String displayLoginPage() {
        System.out.println("***** NavigationController | displayLoginPage ****");
		return "login";
    }

	@RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public String displayUserHomePage() {
		 System.out.println("***** NavigationController | displayUserHomePage ****");
        return "userHome";
    }
	
	@RequestMapping(value = "/adminHome", method = RequestMethod.GET)
    public String displayAdminHomePage() {
		 System.out.println("***** NavigationController | displayAdminHomePage ****");
        return "adminHome";
    }

}
