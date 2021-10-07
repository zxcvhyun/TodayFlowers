package com.example.todayflowers.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserDaoService {
    @Autowired
    private UserRepository userRepository;

    public boolean checkEmailDuplicate(String useremail) {
        if (userRepository.existsByUseremail(useremail)) {
            return false;
        }else {
            return true;
            //return userRepository.existsByUseremail(useremail);
        }

    }

}
