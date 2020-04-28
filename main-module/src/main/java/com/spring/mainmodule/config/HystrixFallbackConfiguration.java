package com.spring.mainmodule.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

@Configuration
public class HystrixFallbackConfiguration {
	
	@Bean
	public FallbackProvider zuulFallbackProviderForUserAuthSvc() {
		return new FallbackProvider() {

			@Override
			public String getRoute() {
				// serviceId property and not the route
				return "user-authentication-service";
			}

			@Override
			public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
				return new ClientHttpResponse() {
					@Override
					public HttpStatus getStatusCode() throws IOException {
						return HttpStatus.OK;
					}

					@Override
					public int getRawStatusCode() throws IOException {
						return HttpStatus.OK.value();
					}

					@Override
					public String getStatusText() throws IOException {
						return HttpStatus.OK.toString();
					}

					@Override
					public void close() {
					}

					@Override
					public InputStream getBody() throws IOException {
						return new ByteArrayInputStream(("user-authentication-service IS DOWN. "
								+ "You are seeing Fall Back response").getBytes());
								//"{\"factorA\":\"Sorry, Service is Down!\",\"factorB\":\"?\",\"id\":null}".getBytes());
					}

					@Override
					public HttpHeaders getHeaders() {
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						return headers;
					}
				};
			}
		};

	}

	@Bean
	public FallbackProvider zuulFallbackProviderForGetGallerySvc() {
		return new FallbackProvider() {

			@Override
			public String getRoute() {
				// serviceId property and not the route
				return "test-getgallery-microservice";
			}

			@Override
			public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
				return new ClientHttpResponse() {
					@Override
					public HttpStatus getStatusCode() throws IOException {
						return HttpStatus.OK;
					}

					@Override
					public int getRawStatusCode() throws IOException {
						return HttpStatus.OK.value();
					}

					@Override
					public String getStatusText() throws IOException {
						return HttpStatus.OK.toString();
					}

					@Override
					public void close() {
					}

					@Override
					public InputStream getBody() throws IOException {
						return new ByteArrayInputStream(("test-getgallery-microservice IS DOWN. "
								+ "You are seeing Fall Back response").getBytes());
								//"{\"factorA\":\"Sorry, Service is Down!\",\"factorB\":\"?\",\"id\":null}".getBytes());
					}

					@Override
					public HttpHeaders getHeaders() {
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						return headers;
					}
				};
			}
		};

	}
	
	@Bean
	public FallbackProvider zuulFallbackProviderForGetImagesSvc() {
		return new FallbackProvider() {

			@Override
			public String getRoute() {
				// serviceId property and not the route
				return "test-getimages-microservice";
			}

			@Override
			public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
				return new ClientHttpResponse() {
					@Override
					public HttpStatus getStatusCode() throws IOException {
						return HttpStatus.OK;
					}

					@Override
					public int getRawStatusCode() throws IOException {
						return HttpStatus.OK.value();
					}

					@Override
					public String getStatusText() throws IOException {
						return HttpStatus.OK.toString();
					}

					@Override
					public void close() {
					}

					@Override
					public InputStream getBody() throws IOException {
						return new ByteArrayInputStream(("test-getimages-microservice IS DOWN. "
								+ "You are seeing Fall Back response").getBytes());
								//"{\"factorA\":\"Sorry, Service is Down!\",\"factorB\":\"?\",\"id\":null}".getBytes());
					}

					@Override
					public HttpHeaders getHeaders() {
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						return headers;
					}
				};
			}
		};

	}
}
