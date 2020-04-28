package com.spring.mainmodule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.mainmodule.entity.JwtRequest;

@Controller
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public void doLogin(@RequestBody JwtRequest authenticationRequest) 
			throws Exception {
		System.out.println("AuthController | doLogin | BEGIN");
		
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();
		System.out.println("AuthController | doLogin | Params:" + username + "----" + password);
		
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

		System.out.println("AuthController | doLogin | END");
	}	
	
}

