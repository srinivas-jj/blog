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

import com.example.blog.dto.CommentRequest;
import com.example.blog.model.Comment;
import com.example.blog.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/blog/{blogPostId}")
    public ResponseEntity<CommentRequest> createComment(@PathVariable int blogPostId,@RequestBody CommentRequest commentRequest){
        CommentRequest createdComment = commentService.createComment(commentRequest.getContent(),blogPostId);
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping("/blog/{blogPostId}")
    public ResponseEntity<List<CommentRequest>> getCommentById(@PathVariable int blogPostId){
        List<CommentRequest> comments = commentService.getCommentById(blogPostId);
        return ResponseEntity.ok(comments);
    }
    
    @PutMapping("/{theId}")
    public ResponseEntity<ResponseEntity<Comment>> updateComment(@PathVariable int theId, @RequestBody Comment commentDetails){
        ResponseEntity<Comment> updatedComment = commentService.updateComment(theId, commentDetails);

        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable int id){
        commentService.deleteComment(id);

        return ResponseEntity.noContent().build();
    }

    
}
