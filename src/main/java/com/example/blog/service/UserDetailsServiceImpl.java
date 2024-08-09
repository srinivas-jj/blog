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

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //The UserDetailsService interface is used to load user-specific data during authentication

    @Autowired
    private UserRepository userRepository;
    /*loadUserByUsername(String username): This method is used to retrieve user details based on the provided username. 
    It returns an instance of UserDetails, which contains the user’s
    credentials and authorities. If the user is not found, it throws a UsernameNotFoundException. */

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userIdentifier) throws UsernameNotFoundException {
        try{
        com.example.blog.model.UserDetail userDetails = userRepository.findByUserName(userIdentifier).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userIdentifier));

        List<SimpleGrantedAuthority> authorities = userDetails.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
            userDetails.getUserName(),
            userDetails.getPassWord(),
            authorities
    );
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        


    }
    
}
