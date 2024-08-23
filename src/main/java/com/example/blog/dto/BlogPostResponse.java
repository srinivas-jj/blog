package com.example.blog.dto;

import java.time.LocalDateTime;

public class BlogPostResponse {

    private int id;

    private String titile;

    private String content;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public BlogPostResponse(int id, String titile, String content, String username, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.titile = titile;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    


    
}
