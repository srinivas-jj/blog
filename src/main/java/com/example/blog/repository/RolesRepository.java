package com.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {

    Roles findByRoleName(String roleName);
    
}
