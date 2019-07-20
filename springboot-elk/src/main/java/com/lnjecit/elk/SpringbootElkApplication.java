package com.lnjecit.elk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringbootElkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootElkApplication.class, args);
    }

    Logger logger = LoggerFactory.getLogger(SpringbootElkApplication.class);

    @GetMapping("elkTest")
    public void elkTest() {
        logger.debug("debug level log");
        logger.info("info level log");
        logger.warn("warn level log");
        logger.error("error level log");
    }

}
