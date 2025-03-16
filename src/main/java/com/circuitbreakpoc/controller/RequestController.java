package com.circuitbreakpoc.controller;

import com.circuitbreakpoc.service.RequestService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    @TimeLimiter(name = "requestTimeLimiter")
    @CircuitBreaker(name = "requestCircuitBreaker", fallbackMethod = "fallback")
    public CompletableFuture<String> getResponse() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return requestService.doRequest();
            } catch (URISyntaxException e) {
                throw new RuntimeException("Error processing request", e);
            }
        });
    }

    public CompletableFuture<String> fallback(Exception ex) {
        String message = ex instanceof TimeoutException ? 
            "Request timed out after 5 seconds" : 
            "Circuit breaker triggered: " + ex.getMessage();
        System.out.println("circuit breaker triggered: " + message);
        return CompletableFuture.completedFuture(message);
    }
}
