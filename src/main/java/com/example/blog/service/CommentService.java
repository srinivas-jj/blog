package com.example.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.blog.model.BlogPost;
import com.example.blog.model.Comment;
import com.example.blog.model.UserDetail;
import com.example.blog.repository.BlogPostRepository;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Comment createComment (String content, int blogPostId){
        UserDetail user = getAuthenticatedUser();
        BlogPost blogPost = blogPostRepository.findById(blogPostId).orElseThrow(()-> new RuntimeException("Blog not found"));
        System.out.println("abcd"+user);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUserDetail(user);
        comment.setBlogPost(blogPost);
        return commentRepository.save(comment);
    }



    public java.util.List<Comment> getAllComments(){

        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(int theId){
        return commentRepository.findById(theId);
    }

    public Comment updateComment(int theId, Comment commentDetails){
        Comment comment = commentRepository.findById(theId)
        .orElseThrow(()->new RuntimeException("comment not found"));

        comment.setContent(commentDetails.getContent());
        comment.setUpdatedAt(commentDetails.getUpdatedAt());

        return commentRepository.save(comment);

    }

    public void deleteComment(int theId){
        Comment comment = commentRepository.findById(theId)
        .orElseThrow(()->new RuntimeException("comment not found"));

        commentRepository.delete(comment);
    }
    private UserDetail getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails userDetails){
            String username = userDetails.getUsername();
            return userRepository.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException("User not found with username:"+ username));
        }
        else{
            throw new UsernameNotFoundException("Authenticated user details not found");

        }
    }
}
