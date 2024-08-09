package com.example.blog.controller;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Comment> createComment(@PathVariable int blogPostId,@RequestBody CommentRequest commentRequest){
        Comment createdComment = commentService.createComment(commentRequest.getContent(),blogPostId);
        return ResponseEntity.ok(createdComment);
    }
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments(){

        List<Comment> comments = commentService.getAllComments();

        return ResponseEntity.ok(comments);

    }

    @GetMapping("/{theId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable int theId){
        Optional<Comment> comment = commentService.getCommentById(theId);
        return comment.map(ResponseEntity:: ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{theId}")
    public ResponseEntity<Comment> updateComment(@PathVariable int theId, @RequestBody Comment commentDetails){
        Comment updatedComment = commentService.updateComment(theId, commentDetails);

        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable int id){
        commentService.deleteComment(id);

        return ResponseEntity.noContent().build();
    }

    
}
