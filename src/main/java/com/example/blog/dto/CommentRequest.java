package com.example.blog.dto;

import com.example.blog.model.BlogPost;

public class CommentRequest {

    private int id;
    private String username;
    private BlogPost blogPost;
    private String content;
    public CommentRequest(int id, String username, BlogPost blogPost, String content) {
        this.id = id;
        this.username = username;
        this.blogPost = blogPost;
        this.content = content;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

   

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    
    
}
