package com.blogapi.bloggingapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.bloggingapi.payload.ApiResponseBody;
import com.blogapi.bloggingapi.payload.CommentDTO;
import com.blogapi.bloggingapi.services.Implementation.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService _commentService;

    // create
    @PostMapping("/comment/post/{postId}")
    public ResponseEntity<CommentDTO> create(
            @Valid @RequestBody CommentDTO commentDTO,
            @PathVariable Integer postId) {
        CommentDTO savedComment = this._commentService.createComment(commentDTO, postId);
        return new ResponseEntity<CommentDTO>(savedComment, HttpStatus.CREATED);
    }

    // delete
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseBody> delete(@PathVariable Integer commentId) {
        this._commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponseBody>(
                new ApiResponseBody("Comment deleted successfully !! ", true), HttpStatus.OK);
    }
}
