package com.spring.mainmodule.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping(value = {"/error", "/errorPage"}, method = RequestMethod.GET)
    public String displayErrorPage() {
        System.out.println("***** CustomErrorController | displayErrorPage ****");
		return "errorPage";
    }
	
	@Override
	public String getErrorPath() {		
		return "errorPage";
	}

}
