package com.example.todayflowers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); //내 서버가 응답할때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는ㄱ ㅓㅅ
        //setAllowCredentials(true) 와 addAllowedorigins("*") 동시에 설정 못함. addAllowedOriginPattern 해야함
        config.addAllowedOriginPattern("*"); //모든 ip에 응답을 허용
        config.addAllowedHeader("*"); //모든  header에 응답
        config.addAllowedMethod("*"); //모든 post get put delete patch 요청 허용
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
