package com.example.todayflowers;

import com.example.todayflowers.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = UserController.class)
public class TodayFlowersApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodayFlowersApplication.class, args);
    }

}
