package com.eureka.gallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient 	// Enable eureka client.
@EnableCircuitBreaker 	// Enable circuit breakers
//@RibbonClient(name = "gateway-service", configuration = RibbonConfiguration.class)
@EnableHystrixDashboard
public class SpringEurekaGalleryApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringEurekaGalleryApp.class, args);
	}
}

@Configuration
class RestTemplateConfig {
	
	// Create a bean for restTemplate to call services
	@Bean
	@LoadBalanced		// Load balance between service instances running at different ports.
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}