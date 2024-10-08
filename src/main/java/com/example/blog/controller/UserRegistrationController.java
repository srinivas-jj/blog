package com.example.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.dto.UserRegistrationDto;
import com.example.blog.model.UserDetail;
import com.example.blog.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        try {
            UserDetail registeredUser = userService.registerUser(userRegistrationDto);
            return new ResponseEntity<>("User Registered Successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
    

