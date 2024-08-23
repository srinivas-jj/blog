package com.example.blog.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blog.dto.UserRegistrationDto;
import com.example.blog.model.Roles;
import com.example.blog.model.UserDetail;
import com.example.blog.repository.RolesRepository;
import com.example.blog.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolesRepository roles;

    @Transactional
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

       Roles userRoles = roles.findByRoleName("ROLE_USER");
       userDetail.setRoles(Collections.singletonList(userRoles));

        UserDetail user = userRepository.save(userDetail);
        System.out.println("User saved with ID: " + user.getId());
        return user;
    }




    
}
