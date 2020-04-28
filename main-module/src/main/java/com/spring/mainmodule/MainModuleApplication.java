package com.spring.mainmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient // It acts as a eureka client
@EnableZuulProxy // Enable Zuul
//Overriding default Ribbon algorithm from Zuul
//@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class MainModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainModuleApplication.class, args);
	}

	/*@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("/*");
			}
		};
	}*/

}
