package com.lnjecit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.lnjecit.distributed.lock.dao")
@SpringBootApplication
public class SpringbootDistributedLockMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDistributedLockMysqlApplication.class, args);
    }
}
