package com.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.model.Comment;

@Repository
public interface  CommentRepository extends JpaRepository<Comment, Integer> {
    
}
