package com.example.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.blog.dto.BlogPostResponse;
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

    private static final Logger logger = LoggerFactory.getLogger(BlogPostService.class);

    //creating the blog post

    public BlogPostResponse createBlogPost(BlogPost blogPost){
        UserDetail author = getAuthenticatedUser();
        blogPost.setAuthor(author);
        BlogPost savedBlogPost =  blogPostRepository.save(blogPost);
        return new BlogPostResponse(
            savedBlogPost.getId(),
            savedBlogPost.getTitle(),
            savedBlogPost.getContent(),
            savedBlogPost.getAuthor().getUserName(),
            savedBlogPost.getCreatedAt(),
            savedBlogPost.getUpdatedAt()
        );
    }

    //get all blog post
    public List<BlogPostResponse> getAllBlogPost(){
        return blogPostRepository.findAll().stream().map(blogpost -> new  BlogPostResponse(
            blogpost.getId(),
            blogpost.getTitle(),
            blogpost.getContent(),
            blogpost.getAuthor().getUserName(),
            blogpost.getCreatedAt(),
            blogpost.getUpdatedAt()
        )).collect(Collectors.toList());
    }

    //return blog post by id
    public BlogPostResponse getBlogPostById(int theId){
        BlogPost blogpost =  blogPostRepository.findById(theId).orElseThrow(()-> new RuntimeException("Blog post not found"));
        return new BlogPostResponse(
            blogpost.getId(),
            blogpost.getTitle(),
            blogpost.getContent(),
            blogpost.getAuthor().getUserName(),
            blogpost.getCreatedAt(),
            blogpost.getUpdatedAt()
        );

    }

    public ResponseEntity<BlogPost> updateBlogPost(int theId, BlogPost blogPostDetails){
        BlogPost blogpost = blogPostRepository.findById(theId).orElseThrow(()-> new RuntimeException("Blog Post not found"));
        UserDetail author = getAuthenticatedUser();

        if(blogpost.getAuthor().getId() != author.getId()){
            String ErrorMessages = "You don't have permisiion to update this Post";
            logger.error(ErrorMessages);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            
        }

        blogpost.setTitle(blogPostDetails.getTitle());
        blogpost.setContent(blogPostDetails.getContent());
        blogpost.setAuthor(author);

        BlogPost updatedBlogPost = blogPostRepository.save(blogpost);
        return ResponseEntity.ok(updatedBlogPost);
    }

    public ResponseEntity<Object> deleteBlogPost(int theId){
        BlogPost blogpost = blogPostRepository.findById(theId)
        .orElseThrow(()->new RuntimeException("post not found"));
        UserDetail user = getAuthenticatedUser();

        if(blogpost.getAuthor().getId() != user.getId()){
            String ErrorMessages = "You don't have permisiion to delete this Post";
            logger.error(ErrorMessages);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);     
        }

        blogPostRepository.delete(blogpost);
        return ResponseEntity.ok("Comment deleted Successfully");
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
