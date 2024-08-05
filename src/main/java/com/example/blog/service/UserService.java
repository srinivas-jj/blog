package com.example.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blog.dto.UserRegistrationDto;
import com.example.blog.model.UserDetail;
import com.example.blog.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetail registerUser(UserRegistrationDto userRegistrationDto){

        if(userRepository.existsByUserName(userRegistrationDto.getUserName())){
        return null;
    }

       // encrypt password
       String encryptesPassword = passwordEncoder.encode(userRegistrationDto.getPassword());
       System.out.println("abcd"+encryptesPassword);


       //create new object
       UserDetail userDetail = new UserDetail();

       userDetail.setUserName(userRegistrationDto.getUserName());
       userDetail.setPassWord(encryptesPassword);
       userDetail.setEmail(userRegistrationDto.getEmail());

        return userRepository.save(userDetail);
    }




    
}
