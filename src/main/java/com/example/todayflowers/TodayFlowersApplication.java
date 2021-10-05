package com.example.todayflowers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.todayflowers.Controller")
public class TodayFlowersApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodayFlowersApplication.class, args);
    }

}
