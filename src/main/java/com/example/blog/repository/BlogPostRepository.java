package com.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.model.BlogPost;

@Repository
public interface BlogPostRepository  extends JpaRepository<BlogPost, Integer>{
    
}
