package com.spring.mainmodule.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.spring.mainmodule.jwt.JwtConfiguration;
import com.spring.mainmodule.jwt.JwtTokenUtil;
import com.spring.mainmodule.service.UserDetailsServiceImpl;

@Component
public class MainModuleAuthSuccessHandler implements AuthenticationSuccessHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private JwtConfiguration jwtConfig;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("***** MainModuleAuthSuccessHandler | onAuthenticationSuccess ****");		
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		final UserDetails userDetails = userDetailsServiceImpl
				.loadUserByUsername(authentication.getName());
		
		final String token = jwtTokenUtil.generateToken(userDetails, authentication);
		System.out.println("***** MainModuleAuthSuccessHandler | onAuthenticationSuccess | token:" + token);
		 
		try {
			handle(request, response, authentication, token);
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}
	
	private void handle(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication, String token) throws IOException, Exception {		
		
		if (response.isCommitted()) {
			// Already committed. Unable to redirect to targetUrl
		}
		
		// Add token to Authorization header
		String tokenValue = jwtConfig.getPrefix() + token;
		response.addHeader(jwtConfig.getHeader(), tokenValue);
		
		// Authorities of the authenticated user
		String roleType = null;
		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			System.out.println(grantedAuthority.getAuthority());
			if ("ROLE_USER".equalsIgnoreCase(grantedAuthority.getAuthority())) {
				roleType = "user";
			} else if ("ROLE_ADMIN".equalsIgnoreCase(grantedAuthority.getAuthority())) {
				roleType = "admin";
			}			
		}
		response.addHeader("Authorization-Role-Type", roleType);
		
		// Cookies
		handleCookies(request, response, tokenValue);

		/*
		// Add token toAuth-Token  header
		response.addHeader("Auth-Token", tokenValue);
		response.setHeader("Access-Control-Expose-Headers", "Auth-Token");
		
		// Use redirect strategy for navigating to Home
		String targetUrl = "/home";
		System.out.println("***** MainModuleAuthSuccessHandler | targetUrl: " + targetUrl + " ****");
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
		*/
	}
	
	private void handleCookies(HttpServletRequest request, HttpServletResponse response,
			String token) throws UnsupportedEncodingException {
		Cookie[] existingCookies = request.getCookies();
		if (existingCookies != null) {
			for (Cookie cookie : existingCookies) {
				cookie.setMaxAge(0);
				cookie.setValue(null);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		
		Cookie newCookie = new Cookie("Auth-Token", URLEncoder.encode(token, "UTF-8"));
		newCookie.setValue(URLEncoder.encode(token, "UTF-8"));
		newCookie.setHttpOnly(true);
		newCookie.setSecure(true);
		newCookie.setMaxAge(600); //10mins
		newCookie.setPath("/");
		response.addCookie(newCookie);
	}

	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

}
