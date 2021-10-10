package com.example.todayflowers.controller;

import com.example.todayflowers.User.User;
import com.example.todayflowers.User.UserDaoService;
import com.example.todayflowers.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    private UserDaoService service;

    public LoginController(UserDaoService service) {
        this.service = service;
    }
//
//
//    @PostMapping("/user/login")
//    public User userLogin(@RequestBody User user) {
//
//        String useremail = String.valueOf(userRepository.findByUseremail(user.getUseremail()));
//        if (userRepository.findByUseremail(user.getUseremail()) == null){
//            System.out.println("이메일을 입력해주세요 .");
//        }else {
//            String pw = userRepository.findByPassword(user.getUseremail());
//            if (Objects.equals(pw, user.getPassword())) {
//                System.out.println("비밀번호 일치");
//            }else {
//                System.out.println("비밀번호 불일치");
//            }
//        }
////        if (!(useremail == null)) {
////            String pw = userRepository.findByPassword(user.getUseremail());
////            if (Objects.equals(pw, user.getPassword())) {
////                System.out.println("비밀번호 일치");
////            }else {
////                System.out.println("비밀번호 불일치");
////            }
////        }else {
////            System.out.println("이메일을 입력해주세요 .");
////        }
//     return user;
//    }
}
