package com.lck.reverse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages={"com.lck.reverse.dao"})
public class ReverseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReverseApplication.class, args);
    }

}
