package com.example.todayflowers.controller;

import com.example.todayflowers.Exception.Message;
import com.example.todayflowers.User.User;
import com.example.todayflowers.User.UserDaoService;
import com.example.todayflowers.User.UserRepository;
import com.example.todayflowers.config.PrincipalDetail;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.security.Principal;

@RestController
@CrossOrigin //인증이 필요한 요청은 이걸로 해결 안됨
@RequiredArgsConstructor
public class MyPageController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDaoService userDaoService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //로그인 된 사람만 들어갈 수 있음
    @GetMapping("/manager/mypage/home")
    public MappingJacksonValue myPageHome(Authentication authentication) {
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        System.out.println("authentication: " + principal.getUsername());
        User userEntity = userRepository.findByUseremail(principal.getUsername());

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "useremail", "password", "phnumber", "address", "joindate", "smsflag", "emailflag");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userEntity);
        mapping.setFilters(filters);

        return mapping;
    }

    // 회원정보 수정
    @PostMapping("/manager/mypage/password/update")
    public ResponseEntity<Message> myPageUpdatePassword (@RequestBody User user) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String password = bCryptPasswordEncoder.encode(user.getPassword());
        User updateUser = userDaoService.update(user.getUseremail(), password);
        System.out.println("updateUser " + updateUser);

        if (updateUser == null) {
            System.out.println("아이디 없음!");
            message.setStatus(Message.StatusEnum.NO_CONTENT);
            message.setSuccess(false);
        }else{
            System.out.println("업데이트 성공");
            message.setStatus(Message.StatusEnum.OK);
            message.setSuccess(true);
        }
        //}

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @PostMapping("/manager/mypage/myinfo/update")
    public ResponseEntity<Message> myPagemyInfoUpdate(@RequestBody User user) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        User updateUser = userDaoService.myPageUpdate(user.getUseremail(), user.getPhnumber(), user.getAddress(), user.getSmsflag(), user.getEmailflag());
        System.out.println("updateUser: " + updateUser);

        System.out.println("회원정보 수정 완료");
        message.setStatus(Message.StatusEnum.OK);
        message.setSuccess(true);
        message.setUser(updateUser.getUseremail());

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

}
