package com.example.todayflowers.controller;

import com.example.todayflowers.Exception.Message;
import com.example.todayflowers.User.User;
import com.example.todayflowers.User.UserDaoService;
import com.example.todayflowers.User.UserRepository;

import com.example.todayflowers.config.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin //인증이 필요한 요청은 이걸로 해결 안됨
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("token")
    public String token() {
        return "<h1> token </h1>";
    }

    @GetMapping("/user/user")
    public String user(Authentication authentication) {
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();
        System.out.println("authentication: " + principal.getUsername());
        return "user";
    }

    @GetMapping("/manager/user")
    public String manager() {
        return "manager";
    }

    @GetMapping("/admin/user")
    public String admin() {
        return "admin";
    }

    @PostMapping("/login/email/search")
    public ResponseEntity<Message> searchEmail(@RequestBody User user) {

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        //String hpnumber = String.valueOf(userRepository.findByHpnumber(user.getHpnumber()));
        User userEntity = userRepository.findByHpnumber(user.getHpnumber());

        if (user.getHpnumber().equals(userEntity.getHpnumber())) {
            message.setStatus(Message.StatusEnum.OK);
            message.setMessage("true");
            message.setData(userEntity.getUseremail());
        }else {
            System.out.println(userEntity.getHpnumber());
            System.out.println(user.getHpnumber());
            message.setStatus(Message.StatusEnum.BAD_REQUEST);
            message.setMessage("false");
        }
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

 //   @PostMapping("/login/password/search")
}
