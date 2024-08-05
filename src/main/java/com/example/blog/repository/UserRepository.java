package com.example.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.model.UserDetail;

@Repository
public interface  UserRepository extends JpaRepository<UserDetail, Integer> {
    
    boolean existsByUserName(String userName);
    Optional<UserDetail> findByUserName(String userName);
}
