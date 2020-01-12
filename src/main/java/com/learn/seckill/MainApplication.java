package com.learn.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = {"com.learn.seckill.dao"})
@EnableCaching
public class MainApplication {

    public static void main(String[] args){
        System.out.println("start spring");
        SpringApplication.run(MainApplication.class,args);
    }
}
