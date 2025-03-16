package com.circuitbreakpoc.service;

import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.CompletableFuture;

@Service
public class RequestService {


    public String doRequest() throws URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080"))
                .GET().timeout(Duration.ofSeconds(20))
                .build();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Boolean> response = restTemplate.exchange("http://localhost:8080/userservice", HttpMethod.GET, httpEntity, Boolean.class);
        if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
            return "request completed";
        }
        return "blank response";
    }


}
