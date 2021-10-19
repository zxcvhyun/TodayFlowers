package com.example.todayflowers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class TestController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/test/get")
    public String getString(){
        return "Get요청";
    }

    @GetMapping("/test/path-variable/{value}")
    public String getValue(@PathVariable String value){
        return value;
    }

}
