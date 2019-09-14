package com.lnjecit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringbooGracefulShutdownApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbooGracefulShutdownApplication.class, args);
    }

}
