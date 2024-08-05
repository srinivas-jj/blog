package com.example.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.blog.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userIdentifier) throws UsernameNotFoundException {

        com.example.blog.model.UserDetail userDetails = userRepository.findByUserName(userIdentifier).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userIdentifier));

        List<SimpleGrantedAuthority> authorities = userDetails.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
            userDetails.getUserName(),
            userDetails.getPassWord(),
            authorities
    );
        
        


    }
    
}
