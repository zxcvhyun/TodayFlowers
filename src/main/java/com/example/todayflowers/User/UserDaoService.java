package com.example.todayflowers.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDaoService {
    @Autowired
    private UserRepository userRepository;
    private static List<User> users = new ArrayList<>();

    public boolean checkEmailDuplicate(String useremail) {
        if (userRepository.existsByUseremail(useremail)) {
            return false;
        }else {
            return true;
            //return userRepository.existsByUseremail(useremail);
        }
    }

    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }


}
