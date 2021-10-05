package com.example.todayflowers.Controller;

import com.example.todayflowers.User.User;
import com.example.todayflowers.User.UserDaoService;
import com.example.todayflowers.User.UserNotFoundException;
import com.example.todayflowers.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
//@RequestMapping("/jpa")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private UserDaoService service;
    public UserController(UserDaoService service){this.service = service;}

    @GetMapping("/users")
    public List<User> retriveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User retriveUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s} Not Found", id));
        }
        return user.get();
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userRepository.findByUseremail(user.getUseremail()) != null) {
            throw new UserNotFoundException(String.format("Useremail[%s} 이미 존재하는 이메일 입니다.", user.getUseremail()));
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

//    @PutMapping("/updateusers/{id}")
//    public ResponseEntity<User>  updateUser(@PathVariable(value = "id")Integer userid , @RequestBody User userdetailed ) {
//        Optional<User> user = userRepository.findById(userid);
//       // user.setId(userdetailed.getId());
//       // user.setUseremail(userdetailed.getUseremail());
//       // final User updatedUser = userRepository.save(user);
//     //   return ResponseEntity.ok(updatedUser);
//
//
//    }
}
