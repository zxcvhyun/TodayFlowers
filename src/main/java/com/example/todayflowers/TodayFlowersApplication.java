package com.example.todayflowers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class TodayFlowersApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodayFlowersApplication.class, args);
    }

}
