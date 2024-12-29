package com.example.microservices.currency_exchange_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {
	
	private Logger logger=LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
	@Retry(name="sample-api", fallbackMethod="fallBackMethod") //by default retry happens 3 times.
	public String sampleApi() {
		logger.info("sample api call received");
		ResponseEntity<String> entity=new RestTemplate().getForEntity("http://localhost:8080/dummy-url", String.class);
		return entity.getBody();
	}
	
	@GetMapping("/sample-api-circuit-breaker")
	@CircuitBreaker(name="default", fallbackMethod="fallBackMethod") //by default retry happens 3 times.
	@RateLimiter(name="default")
	/*
	 * rate limiter is used to allow you to configure how many requests must be allowed within a certain interval of time
	 */
	public String sampleApiCircuitBreaker() {
		logger.info("sample api call received");
		ResponseEntity<String> entity=new RestTemplate().getForEntity("http://localhost:8080/dummy-url", String.class);
		return entity.getBody();
	}
	
	/*
	 * rate limiter is used to allow you to configure how many requests must be allowed within a certain interval of time
	 */
	@GetMapping("/sample-api-rate-limiter")
	@RateLimiter(name="default")
	@Bulkhead(name="default")
	public String sampleApiRateLimiter() {
		return "hello rate limiter concept!";
	}
	
	public String fallBackMethod(Exception exception) {
		return "fall-back-response";
	}


}
