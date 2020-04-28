package com.spring.mainmodule.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.mainmodule.jwt.JwtAuthenticationEntryPoint;
import com.spring.mainmodule.jwt.JwtConfiguration;
import com.spring.mainmodule.jwt.JwtTokenAuthenticationFilter;
import com.spring.mainmodule.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainModuleSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtConfiguration jwtConfig;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private MainModuleAuthSuccessHandler mainModuleAuthSuccessHandler;
	
	@Autowired
	private MainModuleAuthFailureHandler mainModuleAuthFailureHandler;
		
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsServiceImpl)
			.passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(WebSecurity webSecurity) {
		webSecurity.ignoring()
			.antMatchers("/resources/**", "/css/**", "/images/**", "/js/**");
	}
 
	@Override
  	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf().disable()
			    // make sure we use stateless session; session won't be used to store user's state.
		 	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 	
			.and()
			    // handle an authorized attempts 
			    .exceptionHandling()
			    //.authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
			    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()
			   // Add a filter to validate the tokens with every request
			   //.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))	
			   .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), 
					   UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()			   
			   //.antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll() 
			   .antMatchers("/", 
					   "/favicon.ico",
					   "/login", "/doLogin", "/logout",
					   "/error", "/errorPage").permitAll()
			   .antMatchers("/userHome").hasRole("USER")
			   .antMatchers("/adminHome", "/gallery" + "/admin/**").hasRole("ADMIN")			  
			   .anyRequest().authenticated()
			.and()
				.formLogin()
					.loginPage("/login")
					.loginProcessingUrl("/doLogin")
					.successHandler(mainModuleAuthSuccessHandler)
					.failureHandler(mainModuleAuthFailureHandler)
			.and()
				.logout()
					.invalidateHttpSession(true)
					.logoutSuccessUrl("/login")
					.permitAll()
			.and()
				.exceptionHandling()
					.accessDeniedPage("/errorPage");
		
		httpSecurity.headers().frameOptions().sameOrigin();
	}
	
	@Bean
  	public JwtConfiguration jwtConfig() {
    	   return new JwtConfiguration();
  	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}