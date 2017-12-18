package com.lnjecit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.lnjecit.dao")
@SpringBootApplication
public class SpringbootPoiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootPoiApplication.class, args);
	}
}
