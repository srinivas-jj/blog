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

import com.example.blog.dto.CommentRequest;
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

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Transactional
    public CommentRequest createComment (String content, int blogPostId){
        UserDetail user = getAuthenticatedUser();
        BlogPost blogPost = blogPostRepository.findById(blogPostId).orElseThrow(()-> new RuntimeException("Blog not found"));
        System.out.println("abcd"+user);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUserDetail(user);
        comment.setBlogPost(blogPost);
        Comment savedComment = commentRepository.save(comment);

        return new CommentRequest(
            savedComment.getId(),
            savedComment.getUserDetail().getUserName(),
            savedComment.getBlogPost(),
            savedComment.getContent());
    }


    public List<CommentRequest> getCommentById(int theId){
        List<Comment> comments =  commentRepository.findByBlogPostId(theId);
        return comments.stream().map(comment -> new CommentRequest(comment.getId(),
         comment.getUserDetail().getUserName(), 
         comment.getBlogPost(),
         comment.getContent())).collect(Collectors.toList());
    }

    public ResponseEntity<Comment> updateComment(int theId, Comment commentDetails){
        Comment comment = commentRepository.findById(theId)
        .orElseThrow(()->new RuntimeException("comment not found"));

        UserDetail user = getAuthenticatedUser();

        // check if the authenticate user is the owner of the comment 
        if(comment.getUserDetail().getId() != user.getId()){
            String ErrorMessages = "You don't have permisiion to update this comment";
            logger.error(ErrorMessages);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            
        }

        comment.setContent(commentDetails.getContent());
        comment.setUpdatedAt(commentDetails.getUpdatedAt());

        Comment updatedComment = commentRepository.save(comment);

        return ResponseEntity.ok(updatedComment);

    }

    public ResponseEntity<Object> deleteComment(int theId){
        Comment comment = commentRepository.findById(theId)
        .orElseThrow(()->new RuntimeException("comment not found"));
              UserDetail user = getAuthenticatedUser();

        // check if the authenticate user is the owner of the comment 
        if(comment.getUserDetail().getId() != user.getId()){
            String ErrorMessages = "You don't have permisiion to update this comment";
            logger.error(ErrorMessages);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorMessages);
            
        }
        commentRepository.delete(comment);
        return ResponseEntity.ok("Comment deleted Successfully");
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
