package com.circuitbreakpoc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.circuitbreakpoc.controller,com.circuitbreakpoc.service")
public class CircuitbreakpocApplication {

    public static void main(String[] args) {
        SpringApplication.run(CircuitbreakpocApplication.class, args);
    }

}
