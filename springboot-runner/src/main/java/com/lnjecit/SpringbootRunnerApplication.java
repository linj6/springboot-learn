package com.lnjecit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootRunnerApplication {

    public static void main(String[] args) {
        args = new String[]{"欢乐英雄"};
        SpringApplication.run(SpringbootRunnerApplication.class, args);
    }
}
