package com.spring.mainmodule.jwt;

import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter   {
	
	// We use auth manager to validate the user credentials
	private AuthenticationManager authManager;
	
	private final JwtConfiguration jwtConfig;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, 
			JwtConfiguration jwtConfig) {
		this.authManager = authManager;
		this.jwtConfig = jwtConfig;
		
		// By default, UsernamePasswordAuthenticationFilter listens to "/login" path. 
		// In our case, we use "/auth". So, we need to override the defaults.
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			System.out.println("====" + request.getParameter("username") 
			+ "--" + request.getParameter("password"));
			
			// 1. Get credentials from request
			//UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
			
			UserCredentials creds = new UserCredentials(request.getParameter("username") , request.getParameter("password") );
			
			// 2. Create auth object (contains credentials) which will be used by auth manager
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					creds.getUsername(), creds.getPassword(), Collections.emptyList());
			
			// 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
			return authManager.authenticate(authToken);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// Upon successful authentication, generate a token.
	// The 'auth' passed to successfulAuthentication() is the current authenticated user.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		Long now = System.currentTimeMillis();
		String token = Jwts.builder()
			.setSubject(auth.getName())	
			// Convert to list of strings. 
			// This is important because it affects the way we get them back in the Gateway.
			.claim("authorities", auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
			.setIssuedAt(new Date(now))
			.setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
			.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
			.compact();
		
		// Add token to header
		response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
		
		// Add custom header and make it visible in response in front-end
		response.addHeader("Auth-Token", jwtConfig.getPrefix() + token);
		response.setHeader("Access-Control-Expose-Headers", "Auth-Token");
		
		String targetUrl = "/home";
		
		if (response.isCommitted()) {
			// Already committed. Unable to redirect to targetUrl
		}
		
		redirectStrategy.sendRedirect(request, response, targetUrl);
		
	}
	
	// A (temporary) class just to represent the user credentials
	private static class UserCredentials {
	    private String username, password;
	    
	    public UserCredentials(String username, String password) {
	    	this.username = username;
	    	this.password = password;
	    }
	    
	    public String getUsername() {
			return username;
		}
	    
	    public void setUsername(String username) {
			this.username = username;
		}
	    public String getPassword() {
			return password;
		}
	    public void setPassword(String password) {
			this.password = password;
		}
	}
	
	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
}