package com.example.todayflowers.controller;

import com.example.todayflowers.User.User;
import com.example.todayflowers.User.UserDaoService;
import com.example.todayflowers.User.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin //인증이 필요한 요청은 이걸로 해결 안됨
public class LoginController {
    @PostMapping("token")
    public String token() {
        return "<h1> token </h1>";
    }
//    @PostMapping("login")
//    public String login() {
//        return "<h1> login </h1>";
//    }
}
