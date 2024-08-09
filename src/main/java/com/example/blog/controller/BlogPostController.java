
package com.example.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.model.BlogPost;
import com.example.blog.service.BlogPostService;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    

    @PostMapping
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody BlogPost blogPost) {
        BlogPost createdPost = blogPostService.createBlogPost(blogPost);
        System.out.println("abcd"+createdPost);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping
    public List<BlogPost> getAllBlogPosts() {
        return blogPostService.getAllBlogPost();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getBlogPostById(@PathVariable int id) {
        BlogPost blogPost = blogPostService.getBlogPostById(id).orElseThrow(() -> new RuntimeException("Blog post not found"));
        return ResponseEntity.ok(blogPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable int id, @RequestBody BlogPost blogPostDetails) {
        BlogPost updatedPost = blogPostService.updateBlogPost(id, blogPostDetails);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable("id") int theId) {
        blogPostService.deleteBlogPost(theId);
        return ResponseEntity.noContent().build();
    }
}