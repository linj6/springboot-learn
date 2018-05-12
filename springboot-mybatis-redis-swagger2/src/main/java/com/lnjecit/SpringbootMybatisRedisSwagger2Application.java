package com.lnjecit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan({"com.lnjecit.dao", "com.lnjecit.generator.dao"})
@ServletComponentScan
@SpringBootApplication
public class SpringbootMybatisRedisSwagger2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisRedisSwagger2Application.class, args);
	}
}
