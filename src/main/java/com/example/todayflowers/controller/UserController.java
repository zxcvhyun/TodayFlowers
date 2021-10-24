package com.example.todayflowers.controller;

import com.example.todayflowers.User.User;
import com.example.todayflowers.User.UserDaoService;
import com.example.todayflowers.User.UserNotFoundException;
import com.example.todayflowers.User.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private final UserDaoService service;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/users")
    public MappingJacksonValue retriveAllUsers() {
        List<User> users = userRepository.findAll();
        //화면에 보여지는 값 필터링
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "useremail", "joindate", "smsflag", "emailflag");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue retriveUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s} Not Found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "useremail", "joindate", "smsflag", "emailflag");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user.get());
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/user/{useremail}/exists")
    public ResponseEntity<Boolean> chekEmailDuplicate(@PathVariable String useremail) {
        return ResponseEntity.ok(service.checkEmailDuplicate(useremail));
    }

    @PostMapping("/v1/user/login")
    public ResponseEntity<Boolean> createUser(@RequestBody User user) {
        if (!service.checkEmailDuplicate(user.getUseremail())) {
            return ResponseEntity.ok(service.checkEmailDuplicate(user.getUseremail()));
        }else {
            Integer maxid = userRepository.getMaxId();

            if (maxid != null) {
                maxid++;
            } else {
                maxid = 1;
            }

            user.setId(maxid);
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String sysdate = format.format(new Date());
            user.setJoindate(sysdate);

            User savedUser = userRepository.save(user);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedUser.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @PostMapping("/user/join")
    public JSONObject joinUser(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        if (!service.checkEmailDuplicate(user.getUseremail())) {
            jsonObject.put("success", false);
        }else {
            Integer maxid = userRepository.getMaxId();

            if (maxid != null) {
                maxid++;
            } else {
                maxid = 1;
            }

            user.setId(maxid);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String sysdate = format.format(new Date());
            user.setJoindate(sysdate);
            user.setRoles("ROLE_USER");
            User savedUser = userRepository.save(user);

            JSONObject userObject = new JSONObject();
            jsonObject.put("success", true);
            userObject.put("useremail",savedUser.getUseremail());
            userObject.put("password", savedUser.getPassword());
            userObject.put("hpnumber", savedUser.getHpnumber());
            userObject.put("address", savedUser.getAddress());
            userObject.put("joindate", savedUser.getJoindate());
            userObject.put("smsflag", savedUser.getSmsflag());
            userObject.put("emailflag", savedUser.getEmailflag());
            userObject.put("role", savedUser.getRoles());
            jsonObject.put("user", userObject);
        }
        return jsonObject;

    }


}
