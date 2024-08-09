package com.example.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.blog.model.BlogPost;
import com.example.blog.model.UserDetail;
import com.example.blog.repository.BlogPostRepository;
import com.example.blog.repository.UserRepository;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserRepository userRepository;

    //creating the blog post

    public BlogPost createBlogPost(BlogPost blogPost){
        UserDetail author = getAuthenticatedUser();
        blogPost.setAuthor(author);
        return blogPostRepository.save(blogPost);
    }

    //get all blog post
    public List<BlogPost> getAllBlogPost(){
        return blogPostRepository.findAll();
    }

    //return blog post by id
    public Optional<BlogPost> getBlogPostById(int theId){
        return blogPostRepository.findById(theId);
    }

    public BlogPost updateBlogPost(int theId, BlogPost blogPostDetails){
        BlogPost blogpost = blogPostRepository.findById(theId).orElseThrow(()-> new RuntimeException("Blog Post not found"));
        UserDetail author = getAuthenticatedUser();

        blogpost.setTitle(blogPostDetails.getTitle());
        blogpost.setContent(blogPostDetails.getContent());
        blogpost.setAuthor(author);

        return blogPostRepository.save(blogpost);
    }

    public void deleteBlogPost(int theId){
        blogPostRepository.deleteById(theId);
    }


    private UserDetail getAuthenticatedUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    if (principal instanceof UserDetails userDetails) {
        String username = userDetails.getUsername();
        System.out.println("Authenticated username: " + username);
        return userRepository.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    } else {
        System.out.println("Principal is not an instance of UserDetails");
        throw new UsernameNotFoundException("Authenticated user details not found");
    }
}

    
}
